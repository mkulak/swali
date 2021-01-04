from http.server import HTTPServer, BaseHTTPRequestHandler
import os

cmd = "curl http://metadata.google.internal/computeMetadata/v1/instance/%s -H Metadata-Flavor:Google 2>&1 -s -S"

class MyHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        try:
            id = os.popen(cmd % 'id').read()
            hostname = os.popen(cmd % 'hostname').read()
        except:
            id = "undefined"
            hostname = "undefined"

        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()
        response = "Hello from %s %s" % (id, hostname)
        self.wfile.write(response.encode("utf-8"))


print("starting")

try:
    server = HTTPServer(('', 8080), MyHandler)
    server.serve_forever()
except KeyboardInterrupt:
    print('^C received, shutting down server')
    server.socket.close()
