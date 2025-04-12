/// <reference types="Cypress" />

describe('Team Management', () => {
    beforeEach(() => {
        cy.setCookie('org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE', 'en');
        cy.request({
            method: 'POST',
            url: 'api/integration-test/reset-db',
            followRedirect: false
        }).then((response) => {
            expect(response.status).to.eq(200);
        });
        cy.loginByForm('admin.strator@javarush.dev', 'admin');
        cy.visit('/teams');
    });

    it('should be possible to create a new team', () => {
        cy.get('#create-team-button').click();

        cy.url().should('include', '/teams/create');

        cy.get('#id-name').type('Wizards');
        cy.get('#coachId').select('Admin Strator');
        cy.get('#submit-button').click();

        cy.get('#teams-table').find('tbody tr').should('have.length', 1);
    });

    it('should be possible to delete a team', () => {
        cy.request({
            method: 'POST',
            url: 'api/integration-test/add-test-team',
            followRedirect: false
        }).then((response) => {
            expect(response.status).to.eq(200);
        });

        // We should have 1 team to get started
        cy.visit('/teams');
        cy.get('#teams-table').find('tbody tr').should('have.length', 1);

        cy.get('[id^=delete-link-]').click();
        cy.get('#delete-modal-message').contains('Are you sure you want to delete team Test Team?');
        cy.get('#delete-modal-submit-button').click();

        // There should be no team left after the delete
        cy.get('#teams-table').find('tbody tr').should('have.length', 0);

        cy.get('#success-alert-message').contains('Team Test Team was deleted successfully.');

        cy.reload();
        cy.get('#success-alert-message').should('not.exist');
        cy.get('#teams-table').find('tbody tr').should('have.length', 0);
    })
});