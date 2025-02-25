/// <reference types="cypress" />

describe("User management", () => {
    beforeEach(() => {
        cy.setCookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", "en");

        cy.request({
            method: "POST",
            url: "api/integration-test/reset-db",
            followRedirect: false
        }).then((response) => {
            expect(response.status).to.eq(200)
        })

        cy.loginByForm("admin.strator@javarush.dev", "admin")
        cy.visit("/users")
    });

    it("should be able to create a new user", () => {
        cy.get("#create-user-button").click();
        cy.url().should("include", "/users/create");
    });
});