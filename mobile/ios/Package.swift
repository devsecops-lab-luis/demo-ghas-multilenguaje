// swift-tools-version:5.9
// App móvil iOS (Swift) - DEMO INTENCIONALMENTE VULNERABLE
import PackageDescription

let package = Package(
    name: "SahuayoApp",
    platforms: [.iOS(.v15), .macOS(.v13)],
    products: [
        .library(name: "SahuayoApp", targets: ["SahuayoApp"])
    ],
    targets: [
        .target(name: "SahuayoApp", path: "Sources/SahuayoApp")
    ]
)
