# Stable OpenJDK image (LTS)
FROM openjdk:27-ea-trixie

# Metadata
LABEL maintainer="dockerkartikeya.com"

# Create non-root user
RUN useradd -m appuser

# Set working directory
WORKDIR /app

# Copy jar
COPY target/Accounts-0.0.1-SNAPSHOT.jar app.jar

# Fix permissions
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Expose Spring Boot port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
