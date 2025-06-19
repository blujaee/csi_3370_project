"use strict";

// Highlight current active page
document.addEventListener("DOMContentLoaded", function () {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll(".nav-item.nav-page");

    // Bold and highlight the title element for the currently active page
    navLinks.forEach(link => {
        if (currentPath.toLowerCase().trim() === ("/" + String(link.id).trim())) {
            link.classList.add("active", "fw-bold");
            link.style.color = "rgb(255, 255, 255)";
        }

        if ((currentPath.toLowerCase().trim() === "/") && (String(link.id) === "home")) {
            link.classList.add("active", "fw-bold");
            link.style.color = "rgb(255, 255, 255)";
        }
    });
});