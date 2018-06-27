#![allow(unused_imports)]

extern crate ansi_term;
extern crate clap;
extern crate futures;
extern crate hyper;
extern crate hyper_tls;
extern crate serde;
extern crate tokio_core;

use hyper::Client;
use hyper::rt::{self, Future, Stream};
use std::env;
use std::fmt::{Display, Formatter, Result};
use std::io::{self, Write};

fn main() {
    rt::run(rt::lazy(move || {
        let client = Client::new();

        let uri: hyper::Uri = "http://httpbin.org/ip".parse().unwrap();

        client
            .get(uri)
            .map_err(|err| to_failure(err))
            .and_then(|res| {
                res
                    .into_body()
                    .concat2()
                    .map_err(|err| to_failure(err))
                    .and_then(|body| {
                        String::from_utf8(body.into_iter().collect()).map_err(|err| to_failure(err))
                    })
            })
            .map(|res| {
                println!("\n\nDone. {}", res);
            })
            .map_err(|err| {
                eprintln!("Error {}", err);
            })
    }));
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


//M008 MUST: Host should not contain protocol
//    https://zalando.github.io/restful-api-guidelines/#M008
//
