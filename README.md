The Certificate Validator 101 shred validates PEM-formatted certificates, retrieves their trust chain using the BouncyCastle library, and interacts with external services to fetch parent certificates via AIA. It follows Hexagonal Architecture with a domain layer for core business logic, application 