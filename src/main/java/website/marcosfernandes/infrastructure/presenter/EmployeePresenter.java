package website.marcosfernandes.infrastructure.presenter;

import website.marcosfernandes.domain.entity.Employee;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EmployeePresenter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(new Locale("pt", "BR"));
    private static final DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("#,##0.00", SYMBOLS);

    public void printEmployeeList(List<Employee> employees) {
        System.out.println("\n--- Lista de Funcionários ---");
        for (Employee employee : employees) {
            printEmployee(employee);
        }
    }

    public void printEmployee(Employee employee) {
        String formattedDate = formatDate(employee.getBirthDate());
        String formattedSalary = formatCurrency(employee.getSalary());
        System.out.println(String.format(
                "Nome: %s, Data de Nascimento: %s, Salário: %s, Função: %s",
                employee.getName(),
                formattedDate,
                formattedSalary,
                employee.getRole()
        ));
    }

    public void printGroupedByRole(Map<String, List<Employee>> grouped) {
        System.out.println("\n--- Funcionários Agrupados por Função ---");
        grouped.forEach((role, employees) -> {
            System.out.println("\nFunção: " + role);
            for (Employee employee : employees) {
                printEmployee(employee);
            }
        });
    }

    public void printBirthdayMonths(List<Employee> employees) {
        System.out.println("\n--- Funcionários que Fazem Aniversário em Outubro e Dezembro ---");
        if (employees.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
        } else {
            for (Employee employee : employees) {
                printEmployee(employee);
            }
        }
    }

    public void printOldestEmployee(Employee employee, int age) {
        System.out.println("\n--- Funcionário Mais Velho ---");
        System.out.println("Nome: " + employee.getName());
        System.out.println("Idade: " + age + " anos");
    }

    public void printAlphabeticalList(List<Employee> employees) {
        System.out.println("\n--- Funcionários em Ordem Alfabética ---");
        for (Employee employee : employees) {
            printEmployee(employee);
        }
    }

    public void printTotalSalaries(BigDecimal total) {
        System.out.println("\n--- Total de Salários ---");
        System.out.println("Total: " + formatCurrency(total));
    }

    public void printMinimumWageMultiples(List<Employee> employees, BigDecimal minimumWage) {
        System.out.println("\n--- Quantos Salários Mínimos Cada Funcionário Ganha ---");
        for (Employee employee : employees) {
            BigDecimal multiple = employee.getSalary().divide(minimumWage, 2, java.math.RoundingMode.DOWN);
            String formattedMultiple = formatDecimal(multiple);
            System.out.println(employee.getName() + ": " + formattedMultiple);
        }
    }

    private String formatDecimal(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("0.00", SYMBOLS);
        return df.format(value);
    }

    private String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    private String formatCurrency(BigDecimal value) {
        return CURRENCY_FORMATTER.format(value);
    }
}
