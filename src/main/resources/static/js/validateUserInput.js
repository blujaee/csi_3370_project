"use strict";

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("form").addEventListener("submit", function (e) {
        const pwd = this.querySelector("[name='password']").value;
        const confirmPwd = this.querySelector("[name='passwordConfirmation']").value;
        // If password fields are not empty, check if they match
        if (pwd || confirmPwd) {
            if (pwd !== confirmPwd) {
                e.preventDefault();
                alert("Passwords do not match!");
            }
        }
    });

    const phoneInput = document.querySelector("input[name='phone']");
    phoneInput.addEventListener("input", validatePhoneInput);
    function validatePhoneInput(e) {
        let input = e.target.value.replace(/\D/g, "");

        if (input.length > 10) {
            input = input.slice(0, 10);
        }

        let parts = [];
        if (input.length > 0) parts.push(input.slice(0, 3));
        if (input.length > 3) parts.push(input.slice(3, 6));
        if (input.length > 6) parts.push(input.slice(6, 10));

        e.target.value = parts.join("-");
    }

    const ssnInput = document.querySelector("input[name='ssn']");
    ssnInput.addEventListener("input", validateSSNInput);
    function validateSSNInput(e) {
        let input = e.target.value.replace(/\D/g, "");

        if (input.length > 9) {
            input = input.slice(0, 9);
        }

        const parts = [];
        if (input.length > 0) parts.push(input.slice(0, 3));
        if (input.length > 3) parts.push(input.slice(3, 5));
        if (input.length > 5) parts.push(input.slice(5, 9));

        e.target.value = parts.join('-');
    }

    validatePhoneInput({ target: phoneInput });
    validateSSNInput({ target: ssnInput });

    const form = document.querySelector("form");
    const originalValues = {};

    [...form.elements].forEach(el => {
        if (el.name && el.type !== "submit" && el.type !== "password") {
            originalValues[el.name] = el.value;
        }
    });

    form.addEventListener("submit", function (e) {
        const pwd = form.querySelector("[name='password']").value;
        const confirmPwd = form.querySelector("[name='passwordConfirmation']").value;

        if (pwd || confirmPwd) {
            if (pwd !== confirmPwd) {
                e.preventDefault();
                alert('Passwords do not match!');
                return;
            }
        }

        let changed = false;
        [...form.elements].forEach(el => {
            if (
                el.name &&
                el.type !== "submit" &&
                el.type !== "password" &&
                originalValues[el.name] !== el.value
            ) {
                changed = true;
            }
        });

        if (!changed && !pwd && !confirmPwd) {
            e.preventDefault();
            alert("No changes detected.");
        }
    });
});