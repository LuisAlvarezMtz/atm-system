import {getDenominations, withdraw, resetInventory} from "./api.js";

import {
    showMessage,
    clearMessage,
    showInventory,
    showWithdrawResult,
    clearWithdrawResult
} from "./ui.js";

const withdrawForm = document.getElementById("withdraw-form");
const amountInput = document.getElementById("amount");

const withdrawButton = document.getElementById("withdraw-button");
const resetButton = document.getElementById("reset-button");

let loading = false;

// Cargar inventario inicial
async function loadInventory() {

    try {

        const denominations = await getDenominations();

        showInventory(denominations);

    } catch (error) {

        handleError(error);

    }
}

// Retiro de efectivo
withdrawForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    if (loading) return;

    clearMessage();

    const amount = amountInput.value;

    if (!amount || Number(amount) <= 0) {

        showMessage(
            "Ingresa un monto válido.",
            "error"
        );

        return;
    }

    loading = true;
    withdrawButton.disabled = true;

    try {

        const details = await withdraw(amount);

        showWithdrawResult(details);

        showMessage(
            "Retiro realizado correctamente.",
            "success"
        );

        // Actualizar inventario después del retiro.
        await loadInventory();

        amountInput.value = "";

    } catch (error) {

        handleError(error);

    } finally {

        loading = false;
        withdrawButton.disabled = false;

    }

});

// Resetear inventario
resetButton.addEventListener("click", async () => {

    if (loading) return;

    const confirmed = confirm(
        "¿Estás seguro de resetear el inventario?"
    );

    if (!confirmed) return;

    loading = true;
    resetButton.disabled = true;

    try {

        await resetInventory();

        clearWithdrawResult();

        await loadInventory();

        showMessage(
            "Inventario reseteado correctamente.",
            "success"
        );

    } catch (error) {

        handleError(error);

    } finally {

        loading = false;
        resetButton.disabled = false;

    }

});

// Manejo centralizado de errores
function handleError(error) {

    console.error(error);

    if (error.status === 400) {

        if (error.errors) {

            const validationMessages = Object.values(
                error.errors
            ).join(", ");

            showMessage(
                validationMessages || error.message,
                "error"
            );

            return;
        }

        showMessage(
            error.message || "Monto inválido.",
            "error"
        );

        return;
    }

    if (error.status === 409) {

        showMessage(
            error.message || "No se puede dispensar el efectivo solicitado.",
            "error"
        );

        return;
    }

    if (error.status === 500) {

        showMessage(
            "Error interno del servidor.",
            "error"
        );

        return;
    }

    if (!error.status) {

        showMessage(
            "No se pudo conectar con el servidor.",
            "error"
        );

        return;
    }

    showMessage(
        error.message || "Ocurrió un error inesperado.",
        "error"
    );

}

// Ejecutar al cargar la aplicación
clearWithdrawResult();
loadInventory();