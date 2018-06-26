#![allow(unused_imports)]

extern crate futures;
extern crate tokio_core;
extern crate hyper;
extern crate hyper_tls;
extern crate ansi_term;
extern crate clap;
extern crate serde;

use std::env;
use std::io::{self, Write};

use hyper::Client;
use hyper::rt::{self, Future, Stream};

fn main() {
//    println!("Hello, world!");
    rt::run(rt::lazy(move || {
        let client = Client::new();

        let uri: hyper::Uri = "http://httpbin.org/ip".parse().unwrap();

        client
            .get(uri)
            .and_then(|res| {
//                println!("Response: {}", res.status());
//                println!("Headers: {:#?}", res.headers());

                // The body is a stream, and for_each returns a new Future
                // when the stream is finished, and calls the closure on
                // each chunk of the body...
//                res.into_body().for_each(|chunk| {
//                    io::stdout().write_all(&chunk)
//                        .map_err(|e| panic!("example expects stdout is open, error={}", e))
//                })
                res.into_body().map(|chunk| {
                    chunk.iter()
                        .map(|byte| *byte)
                        .collect::<Vec<u8>>()
                })
            })
            .map(|res| {
                println!("\n\nDone.");
            })
            .map_err(|err| {
                eprintln!("Error {}", err);
            })

    }));
}


//M008 MUST: Host should not contain protocol
//    https://zalando.github.io/restful-api-guidelines/#M008
//
