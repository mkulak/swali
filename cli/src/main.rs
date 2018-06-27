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

fn main() {
    rt::run(rt::lazy(move || {
        let https = HttpsConnector::new(1).unwrap();
        let client = Client::builder().build::<_, hyper::Body>(https);

        let uri: hyper::Uri = "https://ijgf82g4o9.execute-api.us-west-2.amazonaws.com/api/supported-rules".parse().unwrap();

        client
            .get(uri)
            .map_err(|err| to_failure(err))
            .and_then(|res| {
                res
                    .into_body()
                    .concat2()
                    .map_err(|err| to_failure(err))
                    .and_then(|body| parse_body(body))
            })
            .map(|res| {
                res.supported_rules.iter().for_each(|rule|
                    println!("{}\n", rule)
                )
            })
            .map_err(|err| {
                eprintln!("Error {}", err);
            })
    }));
}

fn parse_body(body: hyper::Chunk) -> result::Result<SupportedRulesResponse, Failure> {
    let s = String::from_utf8(body.into_iter().collect()).map_err(|err| to_failure(err))?;
    serde_json::from_str(&s).map_err(|err| to_failure(err))
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
    #[serde(rename="type")]
    a_type: String,
    url: String
}


impl Display for SupportedRule {
    fn fmt(&self, f: &mut Formatter) -> Result {
        write!(f, "{} {} {}\n\t{}", self.code, self.a_type, self.title, self.url)
    }
}
