function calculateMortgagePayoff(principal, originationDate, termYears, annualInterestRate) {
    const monthlyInterestRate = annualInterestRate / 12 / 100;
    const totalMonths = termYears * 12;

    const startDate = new Date(originationDate);
    const results = [];

    let balance = principal;

    for (let i = 0; i < totalMonths; i++) {
        const paymentDate = new Date(startDate);
        paymentDate.setMonth(startDate.getMonth() + i);

        const beginningBalance = balance;
        const interestPayment = balance * monthlyInterestRate;
        const principalPayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -totalMonths)) - interestPayment;
        balance -= principalPayment;

        results.push({
            year: paymentDate.getFullYear(),
            month: paymentDate.toLocaleString('default', { month: 'long' }),
            payment: (principalPayment + interestPayment).toFixed(2),
            beginningBalance: beginningBalance.toFixed(2),
            endingBalance: balance.toFixed(2)
        });
    }

    return results;
}

function displayMortgagePayoffTable(principal, originationDate, termYears, annualInterestRate) {
    const results = calculateMortgagePayoff(principal, originationDate, termYears, annualInterestRate);

    let table = `<table border="1">
        <tr>
            <th>Year</th>
            <th>Month</th>
            <th>Payment</th>
            <th>Beginning Balance</th>
            <th>Ending Balance</th>
        </tr>`;

    results.forEach(row => {
        table += `<tr>
            <td>${row.year}</td>
            <td>${row.month}</td>
            <td>$${row.payment}</td>
            <td>$${row.beginningBalance}</td>
            <td>$${row.endingBalance}</td>
        </tr>`;
    });

    table += `</table>`;
    document.getElementById('mortgagePayoffTable').innerHTML = table;
}

// Example usage:
const principal = 0;
const originationDate = '2001-01-01'; // Mortgage origination date
const termYears = 30; // 15-year mortgage
const annualInterestRate = 5;

displayMortgagePayoffTable(principal, originationDate, termYears, annualInterestRate);
