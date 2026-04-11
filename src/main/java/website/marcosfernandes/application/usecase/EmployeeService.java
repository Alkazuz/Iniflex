package website.marcosfernandes.application.usecase;

import website.marcosfernandes.domain.entity.Employee;
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

    public List<Employee> getAllEmployees() {
        return repository.getAll();
    }

    public void removeEmployeeByName(List<Employee> employees, String name) {
        employees.removeIf(employee -> employee.getName().equals(name));
    }

    public void applyTenPercentRaise(List<Employee> employees) {
        employees.forEach(employee -> {
            BigDecimal newSalary = employee.getSalary().multiply(new BigDecimal("1.10"));
            employee.setSalary(newSalary);
        });
    }

    public Map<String, List<Employee>> groupByRole(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getRole));
    }

    public List<Employee> filterByBirthdayMonths(List<Employee> employees, int... months) {
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

    public Employee findOldestEmployee(List<Employee> employees) {
        return employees.stream()
                .min((e1, e2) -> e1.getBirthDate().compareTo(e2.getBirthDate()))
                .orElse(null);
    }

    public List<Employee> sortByNameAscending(List<Employee> employees) {
        return employees.stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                .collect(Collectors.toList());
    }

    public BigDecimal sumAllSalaries(List<Employee> employees) {
        return employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getMinimumWageMultiple(BigDecimal salary, BigDecimal minimumWage) {
        return salary.divide(minimumWage, 2, java.math.RoundingMode.DOWN);
    }

    public int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
