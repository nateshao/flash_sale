from flask import Flask, request, jsonify
import requests

app = Flask(__name__)

FEISHU_TOKEN = '你的飞书机器人token'
FEISHU_URL = f'https://open.feishu.cn/open-apis/bot/v2/hook/{FEISHU_TOKEN}'

@app.route('/feishu/send', methods=['POST'])
def send_alert():
    data = request.json
    alert_text = data['alerts'][0]['annotations']['summary'] + '\n' + data['alerts'][0]['annotations']['description']
    msg = {
        "msg_type": "text",
        "content": {"text": alert_text}
    }
    resp = requests.post(FEISHU_URL, json=msg)
    return jsonify({'code': resp.status_code})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001) 