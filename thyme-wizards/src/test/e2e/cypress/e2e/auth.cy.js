/// <reference types="cypress" />

describe("Authentication", () => {
    beforeEach(() => {
        cy.setCookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", "en");

        cy.request({
            method: "POST",
            url: "api/integration-test/reset-db",
            followRedirect: false
        }).then((response) => {
            expect(response.status).to.eq(200);
        })
    });

    it("should be possible to login as a user", () => {
        cy.visit("/login");

        cy.get("#username").type("jon.doe@javarush.dev");
        cy.get("#password").type("jon");
        cy.get("#submit-button").click();

        cy.url().should("include", "/users");
    });

    it("should be able to login as an admin", () => {
        cy.visit("/login");

        cy.get("#username").type("admin.strator@javarush.dev");
        cy.get("#password").type("admin");
        cy.get("#submit-button").click();

        cy.url().should("include", "/users");
    });
});