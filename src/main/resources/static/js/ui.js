const inventoryElement = document.getElementById("inventory");
const resultElement = document.getElementById("withdraw-result");
const messageElement = document.getElementById("message");

export function showMessage(message, type = "success") {

    messageElement.textContent = message;

    messageElement.className = `message ${type}`;
}

export function clearMessage() {

    messageElement.textContent = "";
    messageElement.className = "message hidden";
}

// #inventory ya es un <tbody>, por eso solo se insertan filas <tr>.
export function showInventory(denominations) {

    if (!denominations || denominations.length === 0) {

        inventoryElement.innerHTML = `
            <tr>
                <td colspan="3" class="empty">No hay denominaciones disponibles.</td>
            </tr>
        `;

        return;
    }

    inventoryElement.innerHTML = denominations.map(denomination => `
        <tr>
            <td>${formatDenominationType(denomination.denominationType)}</td>
            <td>${formatCurrency(denomination.value)}</td>
            <td>${denomination.quantity}</td>
        </tr>
    `).join("");
}

export function showWithdrawResult(details) {

    resultElement.classList.remove("hidden");

    if (!details || details.length === 0) {

        resultElement.innerHTML = `
            <p class="empty">No se entregaron denominaciones.</p>
        `;

        return;
    }

    const rows = details.map(detail => `
        <tr>
            <td>${formatDenominationType(detail.denominationType)}</td>
            <td>${formatCurrency(detail.value)}</td>
            <td>${detail.quantity}</td>
            <td>${formatCurrency(detail.subTotal)}</td>
        </tr>
    `).join("");

    const total = details.reduce(
        (sum, detail) => sum + Number(detail.subTotal),
        0
    );

    resultElement.innerHTML = `
        <h3>Detalle del retiro</h3>

        <table>
            <thead>
                <tr>
                    <th>Tipo</th>
                    <th>Denominación</th>
                    <th>Cantidad</th>
                    <th>Subtotal</th>
                </tr>
            </thead>

            <tbody>
                ${rows}
            </tbody>
        </table>

        <p class="total">
            Total entregado: ${formatCurrency(total)}
        </p>
    `;
}

export function clearWithdrawResult() {

    resultElement.classList.remove("hidden");

    resultElement.innerHTML = `
        <p class="empty">
            Aún no se ha realizado ningún retiro.
        </p>
    `;
}

// Mapeo del tipo de denominación del backend a texto en español.
const DENOMINATION_LABELS = {
    BANKNORT: "Billete",
    BANKNOTE: "Billete",
    COIN: "Moneda"
};

export function formatDenominationType(type) {

    return DENOMINATION_LABELS[type] ?? type;
}

export function formatCurrency(value) {

    return new Intl.NumberFormat("es-MX", {
        style: "currency",
        currency: "MXN"
    }).format(value);
}