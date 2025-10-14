# Emotion Detection API

This is a simple Flask application that provides an API for emotion detection in images.

## How to Use

To use the API, send a POST request to the `/emotion` endpoint with an image file.

### Example using cURL

```bash
curl -X POST -F "file=@/path/to/your/image.jpg" http://localhost:7860/emotion
```

### Example Response

```json
{
  "emotion": "happy"
}
```

## Docker Deployment

To build and run the Docker container, use the following commands:

```bash
docker build -t emotion-detection .
docker run -p 7860:7860 emotion-detection
```