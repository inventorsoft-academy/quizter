export class ErrorResponse {
    constructor(response) {
        this.time = response.time;
        this.message = response.message;
        this.details = response.details;
    }
}