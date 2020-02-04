# flask_web/app.py

from flask import Flask
app = Flask(__name__)


@app.route('/')
def hello_world():
    return '<html><body style="background:black"><h1 style="color:orange">Welcome to OpsSchool Project Page</h1></html>'


@app.route('/goaway')
def goaway():
    return 'GO AWAY!'


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
