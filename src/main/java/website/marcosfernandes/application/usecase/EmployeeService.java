package website.marcosfernandes.application.usecase;

import website.marcosfernandes.domain.entity.Funcionario;
import website.marcosfernandes.infrastructure.data.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> getAllEmployees() {
        return repository.getAll();
    }

    public void removeEmployeeByName(List<Funcionario> employees, String name) {
        employees.removeIf(employee -> employee.getName().equals(name));
    }

    public void applyTenPercentRaise(List<Funcionario> employees) {
        employees.forEach(employee -> {
            BigDecimal newSalary = employee.getSalary().multiply(new BigDecimal("1.10"));
            employee.setSalary(newSalary);
        });
    }

    public Map<String, List<Funcionario>> groupByRole(List<Funcionario> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Funcionario::getRole));
    }

    public List<Funcionario> filterByBirthdayMonths(List<Funcionario> employees, int... months) {
        return employees.stream()
                .filter(employee -> {
                    int employeeMonth = employee.getBirthDate().getMonthValue();
                    for (int month : months) {
                        if (employeeMonth == month) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public Funcionario findOldestEmployee(List<Funcionario> employees) {
        return employees.stream()
                .min((e1, e2) -> e1.getBirthDate().compareTo(e2.getBirthDate()))
                .orElse(null);
    }

    public List<Funcionario> sortByNameAscending(List<Funcionario> employees) {
        return employees.stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                .collect(Collectors.toList());
    }

    public BigDecimal sumAllSalaries(List<Funcionario> employees) {
        return employees.stream()
                .map(Funcionario::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getMinimumWageMultiple(BigDecimal salary, BigDecimal minimumWage) {
        return salary.divide(minimumWage, 2, java.math.RoundingMode.DOWN);
    }

    public int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
