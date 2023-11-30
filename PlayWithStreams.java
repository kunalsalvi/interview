import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class PlayWithStreams {

    private static List<Student> list = Arrays.asList(new Student(1, "Aditya", "Mall", 30, "Male", "Mechanical Engineering", 2014, "Mumbai", 122), new Student(2, "Pulkith", "Singh", 26, "Male", "Computer Engineering", 2018, "Delhi", 67), new Student(3, "Ankita", "Patil", 25, "Female", "Computer Engineering", 2019, "Kerala", 164), new Student(4, "Satish", "Malaghan", 30, "Male", "Mechanical Engineering", 2014, "Kerala", 26), new Student(5, "Darshan", "Mukd", 23, "Male", "Instrumentation Engineering", 2022, "Mumbai", 12), new Student(6, "Chetan", "Star", 24, "Male", "Mechanical Engineering", 2023, "Karnataka", 90), new Student(7, "Arun", "Vittal", 26, "Male", "Electronics Engineering", 2014, "Karnataka", 324), new Student(8, "Nam", "Dev", 31, "Male", "Computer Engineering", 2014, "Karnataka", 433), new Student(9, "Sonu", "Shankar", 27, "Female", "Computer Engineering", 2018, "Karnataka", 7), new Student(10, "Satyam", "Pandey", 26, "Male", "Biotech Engineering", 2017, "Mumbai", 98));

    public static void main(String[] args) {

        // 1 - Group by department
        Map<String, List<Student>> studentsByDepartment = list.stream().collect(groupingBy(Student::departmantName));
        System.out.println("\nStudents by Department " + studentsByDepartment);

        // 2 - count of students in each department
        Map<String, Long> departmentWiseCount = list.stream().collect(groupingBy(Student::departmantName, counting()));
        System.out.println("\nDepartment-wise count " + departmentWiseCount);

        // 3 - All department names
        List<String> listOfDepartments = list.stream().map(Student::departmantName).distinct().toList();
        System.out.println("\nList of Departments" + listOfDepartments);

        // 4 - List of students with age < 25
        List<Student> studentsWithAgeLessThan25 = list.stream().filter(s -> s.age() < 25).toList();
        System.out.println("\nStudents with age less than 25");

        // 5 - Find the max age of students
        Optional<Student> studentMaxAge = list.stream().max(Comparator.comparingInt(Student::age));
        studentMaxAge.ifPresent(s -> System.out.println("\nStudent maximum age is " + s.age()));

        // 6 - Average of male and female students
        Map<String, Double> maleFemaleAvgAgeMap = list.stream().collect(groupingBy(Student::gender, averagingInt(Student::age)));
        System.out.println("\nAverage Age of Female " + maleFemaleAvgAgeMap.get("Female"));
        System.out.println("\nAverage Age of Male " + maleFemaleAvgAgeMap.get("Male"));

        // 7 - Young student by departments
        Map<String, Optional<Student>> minAgeStudentByDepartment = list.stream().collect(groupingBy(Student::departmantName, minBy(Comparator.comparingInt(Student::age))));
        System.out.println("\nStudent with minimum age in each department");
        minAgeStudentByDepartment.forEach((k, v) -> {
            System.out.println("\nDepartment - " + k + " ");
            v.ifPresent(student -> {
                System.out.println(student.age());
            });
        });

        Optional<Student> youngStudent = list.stream().min(Comparator.comparingInt(Student::age));
        youngStudent.ifPresent(s -> System.out.println("\nYoung Student " + s.age()));

        // 8 - Senior Female in all departments
        Optional<Student> seniorFemaleStudent = list.stream().filter(student -> student.gender().equalsIgnoreCase("female")).max(Comparator.comparingInt(Student::age));
        seniorFemaleStudent.ifPresent(s -> System.out.println("\nSenior Female Student " + s.firstName() + " " + s.age()));

        // 9 - List of Students whose rank is between 50 and 100
        List<String> studentRankedBetween50And100 = list.stream().filter(s -> s.rank() > 50 && s.rank() < 100).map(Student::firstName).toList();
        System.out.println("Student Ranked between 50 and 100 " + studentRankedBetween50And100);

        // 10 - Department with maximum number of students
        list.stream()
                .collect(groupingBy(Student::departmantName, counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(s -> System.out.println("\nDepartment having maximum number of students " + s));

        // 11 - students staying in Mumbai
        List<String> mumbaiCityStudents = list.stream().filter(student -> student.city().equalsIgnoreCase("Mumbai")).map(student -> student.firstName()).toList();
        System.out.println("\n Students staying in Mumbai " + mumbaiCityStudents);

        // 12 - total count of students
        long count = list.size();

        // 13 - Average rank in all departments
        Map<String, Double> departmentWiseAvgRank = list.stream().collect(groupingBy(Student::departmantName, averagingInt(Student::rank)));
        System.out.println("\nDepartment wise average rank");
        departmentWiseAvgRank.forEach((k,v) -> {
            System.out.println("Department - " + k + "  " + v);
        });

        // 14 - High rank in each department
        Map<String, Optional<Student>> highestRankDepartmentWise = list.stream().collect(groupingBy(Student::departmantName, maxBy(Comparator.comparingInt(Student::rank))));
        System.out.println("\nDepartment wise highest rank ");
        highestRankDepartmentWise.forEach((k,v) -> {
            v.ifPresent(student -> System.out.println("Department - " + k + " " + student.rank()));
        });

        // 15 - List of students sorted by their rank
        List<String> studentsByRank = list.stream().sorted(Comparator.comparingInt(Student::rank)).map(Student::firstName).toList();
        System.out.println("\nStudents by rank" + studentsByRank);

        // 16 - Second highest rank student
        list.stream().sorted(Comparator.comparingInt(Student::rank))
                .skip(1)
                .findFirst().ifPresent(student -> System.out.println("\nSecond Highest ranked Student " + student.firstName()));

        // 17 - Ranks of students in each department in ascending order
        Map<String, List<Student>> studentRankSortedDepartmentWise = list.stream().collect(groupingBy(Student::departmantName, collectingAndThen(toList(), list -> {
            return list.stream().sorted(Comparator.comparingInt(Student::rank)).collect(toList());
        })));

        studentRankSortedDepartmentWise.forEach((k,v) -> {
            System.out.println("Department - " + k + " " + v);
        });


    }

}

record Student(int id, String firstName, String lastName, int age, String gender, String departmantName, int joinedYear,
               String city, int rank) {
}