package Contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Returns state of the asked request")
    request {
        method GET()
        url("/api/requests/10/state")

    }
    response {
        body(
                        "PENDING"
        )
        status(200)
    }
}
