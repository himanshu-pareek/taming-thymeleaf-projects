/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
  content: ['./src/main/resources/templates/**/*.html', './src/main/resources/templates/**/*.svg'],
  theme: {
    extend: {
      colors: {
        'taming-thymeleaf-green': 'darkseagreen'
      },
      fontFamily: {
        sans: ['Inter var', ...defaultTheme.fontFamily.sans],
      }
    },
    plugins: [],
  },
  plugins: [
    require('@tailwindcss/forms')
  ],
}

