ARG EKL_VERSION
FROM docker.elastic.co/elasticsearch/elasticsearch:${EKL_VERSION}
COPY settings config/settings
RUN elasticsearch-plugin install analysis-nori