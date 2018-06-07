Swali - swagger linter
==
 
Lint your Swagger-based REST API according to [Zalando RESTful API guidelines](https://opensource.zalando.com/restful-api-guidelines).
This project is fork of [Zally](https://github.com/zalando/zally).

Swali is deployed as [AWS Lambda](https://aws.amazon.com/lambda/)
 
 
How to use
---

Swali itself has [REST API](backend/src/main/resources/api.yaml). 

Address of deployed version: https://ijgf82g4o9.execute-api.us-west-2.amazonaws.com/api (no auth required).

Send request via command line:
 
```bash
echo '{"api_definition_url": "http://petstore.swagger.io/v2/swagger.json", 
"ignore_rules": ["172", "146", "150", "110", "176", "151", "143", "174", "129"]}' | 
http POST https://ijgf82g4o9.execute-api.us-west-2.amazonaws.com/api/violations
``` 

CLI and Web UI is in development.
