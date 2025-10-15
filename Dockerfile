# Use an official Python runtime as a parent image
FROM python:3.9-slim

# Create a non-root user and group
RUN addgroup --system app && adduser --system --group app

# Install system dependencies for OpenCV
RUN apt-get update && apt-get install -y --no-install-recommends libgl1 libglib2.0-0 && rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Set DEEPFACE_HOME environment variable
# Deepface will try to create /app/.deepface/weights
ENV DEEPFACE_HOME=/app

# Copy requirements and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application code
COPY . .

# Change ownership of the app directory to the app user
# This is done AFTER all files are copied and dependencies installed
RUN chown -R app:app /app

# Switch to the non-root user
USER app

# Make port 7860 available to the world outside this container
EXPOSE 7860

# Define environment variable
ENV PORT 7860

# Run app.py when the container launches
CMD ["python", "app.py"]