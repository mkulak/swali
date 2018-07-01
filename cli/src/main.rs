#![allow(unused_imports)]

extern crate ansi_term;
extern crate clap;
extern crate futures;
extern crate hyper;
extern crate hyper_tls;
extern crate serde;
extern crate serde_json;
extern crate tokio_core;

#[macro_use]
extern crate serde_derive;

use hyper::Client;
use hyper::rt::{self, Future, Stream};
use hyper_tls::HttpsConnector;
use std::env;
use std::fmt::{Display, Formatter, Result};
use std::io::{self, Write};
use std::result;
use serde_json::Error;
use ansi_term::Colour;
use ansi_term::Style;
use clap::{Arg, App, SubCommand, AppSettings};

static DEFAULT_URL: &str =
    "https://ijgf82g4o9.execute-api.us-west-2.amazonaws.com/api/supported-rules";

fn main() {
    let matches = App::new("Swali CLI")
        .version("0.5")
        .about("Lints Swagger API")
        .setting(AppSettings::SubcommandRequired)
        .arg(Arg::with_name("swali_url").short("l").long("linter-service").env("SWALI_URL").takes_value(true))
        .subcommand(SubCommand::with_name("rules").about("List supported rules"))
        .subcommand(SubCommand::with_name("lint").about("Lint swagger API definition  from file/url"))
        .get_matches();

    rt::run(rt::lazy(move || {
        let uri_str = matches.value_of("swali_url").unwrap_or(DEFAULT_URL);
        println!("uri_str: {}", uri_str);
        let uri = uri_str.parse::<hyper::Uri>().unwrap();

        match matches.subcommand_name() {
            Some("rules") => {
                println!("should print rules");
                print_rules(uri);
            }
            Some("lint") => println!("should lint"),
            _ => println!("nothing to do")
        };
    }));
}

fn print_rules<'a>(uri: hyper::Uri) {
    let https = HttpsConnector::new(1).unwrap();
    let client = Client::builder().build::<_, hyper::Body>(https);
    client
        .get(uri)
        .and_then(|res| res.into_body().concat2())
        .map_err(to_failure)
        .and_then(parse_body)
        .map(|res| {
            res.supported_rules.iter().for_each(|rule|
                println!("{}\n", rule)
            )
        })
        .map_err(|err| eprintln!("Error {}", err))
}

fn parse_body(body: hyper::Chunk) -> result::Result<SupportedRulesResponse, Failure> {
    let s = String::from_utf8(body.into_iter().collect()).map_err(to_failure)?;
    serde_json::from_str(&s).map_err(to_failure)
}

fn to_failure<A: Display>(a: A) -> Failure {
    Failure { show: format!("Failure: {}", a) }
}

struct Failure { show: String }

impl Display for Failure {
    fn fmt(&self, f: &mut Formatter) -> Result {
        write!(f, "{}", self.show)
    }
}

#[derive(Serialize, Deserialize)]
struct SupportedRulesResponse {
    supported_rules: Vec<SupportedRule>
}

#[derive(Serialize, Deserialize)]
struct SupportedRule {
    title: String,
    code: String,
    #[serde(rename = "type")]
    a_type: String,
    url: String,
}


impl Display for SupportedRule {
    fn fmt(&self, f: &mut Formatter) -> Result {
        let styled_title = Style::new().bold().paint(self.title.as_str());
        let type_ptr = self.a_type.as_str();
        let styled_type = Style::new().bold().fg(get_color(type_ptr)).paint(type_ptr);
        write!(f, "{} {} {}\n\t{}", self.code, styled_type, styled_title, self.url)
    }
}

fn get_color(a_type: &str) -> Colour {
    match a_type {
        "MUST" => Colour::Red,
        "SHOULD" => Colour::Yellow,
        _ => Colour::Green
    }
}

//Command:
//    rules List supported rules
//    lint Lint given file/url with API definition
//Params:
//    linter-service, l - url of linter service, ENV VAR SWALI_URL
