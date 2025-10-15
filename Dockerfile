# Use an official Python runtime as a parent image
FROM python:3.9-slim

# Set the working directory in the container
WORKDIR /app

# Create a non-root user and group
RUN addgroup --system app && adduser --system --group app

# Set DEEPFACE_HOME environment variable
ENV DEEPFACE_HOME=/app

# Create a directory for deepface models and set permissions
RUN mkdir -p /app/.deepface && chown -R app:app /app/

# Install system dependencies for OpenCV
RUN apt-get update && apt-get install -y libgl1 libglib2.0-0

# Copy the requirements file into the container at /app
COPY --chown=app:app requirements.txt .

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application's code into the container at /app
COPY --chown=app:app . .

# Switch to the non-root user
USER app

# Make port 7860 available to the world outside this container
EXPOSE 7860

# Define environment variable
ENV PORT 7860

# Run app.py when the container launches
CMD ["python", "app.py"]