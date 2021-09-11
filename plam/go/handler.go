package main

import (
	"net/http"

	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
)

func show(req events.APIGatewayProxyRequest) (events.APIGatewayProxyResponse, error) {
	isbn := req.QueryStringParameters["foo"]

	return events.APIGatewayProxyResponse{
		StatusCode: http.StatusOK,
		Body:       string(isbn),
	}, nil
}

func main() {
	lambda.Start(show)
}
