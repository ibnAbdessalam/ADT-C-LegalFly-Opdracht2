package Contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("should return request with non null id")
    request {
        method POST()
        url("/api/requests/AddRequest")
        headers { contentType applicationJson() }
        body(
                [
                        "title": "test ",
                        "description": "i want this to be verified",
                        "clientEmail": "ahmed@alhilou.be"
                ]
        )
    }
    response {
        body(
                [
                        "title": "test ",
                        "status": "PENDING",
                        "description": "i want this to be verified",
                        "clientEmail": "ahmed@alhilou.be"
                ]
        )
        status(201)
    }
}


