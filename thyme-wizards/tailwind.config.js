/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
  content: ['./src/main/resources/templates/**/*.html'],
  theme: {
    extend: {
      colors: {
        'taming-thymeleaf-green': 'darkseagreen'
      },
      fontFamily: {
        sans: ['Inter var', ...defaultTheme.theme.fontFamily.sans],
      }
    },
    plugins: [],
  },
  plugins: [],
}

