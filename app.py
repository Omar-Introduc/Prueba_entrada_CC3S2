import cv2
from deepface import DeepFace
from flask import Flask, request, jsonify
import numpy as np
import os

app = Flask(__name__)

# Load face cascade classifier
face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

@app.route('/emotion', methods=['POST'])
def emotion_detection():
    if 'file' not in request.files:
        return jsonify({'error': 'No file part in the request'}), 400
    file = request.files['file']
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400
    if file:
        # Read image file
        filestr = file.read()
        npimg = np.frombuffer(filestr, np.uint8)
        frame = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

        gray_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        # Convert grayscale frame to RGB format
        rgb_frame = cv2.cvtColor(gray_frame, cv2.COLOR_GRAY2RGB)
        # Detect faces in the frame
        faces = face_cascade.detectMultiScale(gray_frame, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))

        if len(faces) == 0:
            return jsonify({'emotion': 'No face detected'})

        emocion = "Sin Emocion"
        for (x, y, w, h) in faces:
            face_roi = rgb_frame[y:y + h, x:x + w]
            # Perform emotion analysis on the face ROI
            try:
                result = DeepFace.analyze(face_roi, actions=['emotion'], enforce_detection=False)
                # Determine the dominant emotion
                emocion = result[0]['dominant_emotion']
            except Exception as e:
                return jsonify({'error': str(e)}), 500

        return jsonify({'emotion': emocion})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=int(os.environ.get('PORT', 7860)))