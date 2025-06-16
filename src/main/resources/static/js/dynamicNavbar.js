"use strict";

// Highlight current active page
document.addEventListener("DOMContentLoaded", function () {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll(".nav-item");

    // Bold and highlight the title element for the currently active page
    // Assumes the HTML file has the SAME NAME as the href (/home, /login, /add, etc.)
    navLinks.forEach(link => {
        const linkPath = new URL(link.href).pathname;

        if (currentPath.toLowerCase() === linkPath.toLowerCase()) {
            link.classList.add("active", "fw-bold");
            link.style.color = "rgb(255, 255, 255)";
        }
    });
});