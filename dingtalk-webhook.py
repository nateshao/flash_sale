from flask import Flask, request, jsonify
import requests

app = Flask(__name__)

DINGTALK_TOKEN = '你的钉钉机器人token'
DINGTALK_URL = f'https://oapi.dingtalk.com/robot/send?access_token={DINGTALK_TOKEN}'

@app.route('/dingtalk/send', methods=['POST'])
def send_alert():
    data = request.json
    alert_text = data['alerts'][0]['annotations']['summary'] + '\n' + data['alerts'][0]['annotations']['description']
    msg = {
        "msgtype": "text",
        "text": {"content": alert_text}
    }
    resp = requests.post(DINGTALK_URL, json=msg)
    return jsonify({'code': resp.status_code})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) 