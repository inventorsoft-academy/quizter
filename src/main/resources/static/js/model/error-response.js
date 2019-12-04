export class ErrorResponse {
    constructor(response) {
        this.time = response.time;
        this.message = new Map(JSON.parse(response.message));
        this.details = response.details;
    }
}