import cv2
from deepface import DeepFace
# Load face cascade classifier
face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
frame = cv2.imread('Mario_Vargas_Llosa.jpg')
gray_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
# Convert grayscale frame to RGB format
rgb_frame = cv2.cvtColor(gray_frame, cv2.COLOR_GRAY2RGB)
# Detect faces in the frame
faces = face_cascade.detectMultiScale(gray_frame, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))
emocion = "Sin Emocion"
for (x, y, w, h) in faces:
    face_roi = rgb_frame[y:y + h, x:x + w]
# Perform emotion analysis on the face ROI
    result = DeepFace.analyze(face_roi, actions=['emotion'], enforce_detection=False)
# Determine the dominant emotion
    emocion = result[0]['dominant_emotion']
print("Emocion:", emocion)
