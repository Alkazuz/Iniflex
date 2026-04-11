package website.marcosfernandes;

import website.marcosfernandes.application.usecase.EmployeeService;
import website.marcosfernandes.domain.entity.Funcionario;
import website.marcosfernandes.infrastructure.data.EmployeeRepository;
import website.marcosfernandes.infrastructure.presenter.EmployeePresenter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        EmployeeService service = new EmployeeService(repository);
        EmployeePresenter presenter = new EmployeePresenter();

        List<Funcionario> employees = service.getAllEmployees();

        // 3.1 - Inserir todos os funcionários (já feito no repositório)
        // 3.2 - Remover João
        service.removeEmployeeByName(employees, "João");

        // 3.3 - Imprimir todos os funcionários
        presenter.printEmployeeList(employees);

        // 3.4 - Aplicar aumento de 10%
        service.applyTenPercentRaise(employees);

        // 3.5 - Agrupar por função
        Map<String, List<Funcionario>> grouped = service.groupByRole(employees);

        // 3.6 - Imprimir agrupados por função
        presenter.printGroupedByRole(grouped);

        // 3.8 - Funcionários que fazem aniversário em outubro (10) e dezembro (12)
        List<Funcionario> birthdayEmployees = service.filterByBirthdayMonths(employees, 10, 12);
        presenter.printBirthdayMonths(birthdayEmployees);

        // 3.9 - Funcionário mais velho
        Funcionario oldest = service.findOldestEmployee(employees);
        if (oldest != null) {
            int age = service.calculateAge(oldest.getBirthDate());
            presenter.printOldestEmployee(oldest, age);
        }

        // 3.10 - Lista em ordem alfabética
        List<Funcionario> alphabetical = service.sortByNameAscending(employees);
        presenter.printAlphabeticalList(alphabetical);

        // 3.11 - Total de salários
        BigDecimal totalSalaries = service.sumAllSalaries(employees);
        presenter.printTotalSalaries(totalSalaries);

        // 3.12 - Quantos salários mínimos cada funcionário ganha
        BigDecimal minimumWage = new BigDecimal("1212.00");
        presenter.printMinimumWageMultiples(employees, minimumWage);
    }
}
