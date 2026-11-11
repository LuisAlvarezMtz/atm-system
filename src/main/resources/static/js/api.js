const API_URL = "/api/v1/atm";

async function request(endpoint, options = {}) {

    const response = await fetch(`${API_URL}${endpoint}`, {
        ...options,

        cache: "no-store",

        headers: {
            Accept: "application/json",
            ...options.headers
        }
    });

    if (response.status === 204) {
        return null;
    }

    const text = await response.text();

    let data = null;

    if (text) {
        try {
            data = JSON.parse(text);
        } catch {
            data = { detail: text };
        }
    }

    if (!response.ok) {

        const error = new Error(
            data?.detail || data?.title || "Ocurrió un error"
        );

        error.status = response.status;
        error.title = data?.title;
        error.type = data?.type;
        error.errors = data?.errors;
        error.timestamp = data?.timestamp;

        throw error;
    }

    return data;
}

// GET /api/v1/atm/denominations
export function getDenominations() {

    return request("/denominations", {
        method: "GET"
    });
}

// POST /api/v1/atm/withdraw
export function withdraw(amount) {

    return request("/withdraw", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            amount: Number(amount)
        })
    });
}

// POST /api/v1/atm/reset
export function resetInventory() {

    return request("/reset", {
        method: "POST"
    });
}