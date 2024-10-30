import java.sql.*;
import java.util.Scanner;


public class ConexionMySQL {
    // Configuración de la conexión
    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;
        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project",
                    "root",
                    ""
            );

            System.out.println("Genial, nos conectamos");

            //Declaracion del scanner
            Scanner scanner = new Scanner(System.in);
            int option ;


            do {
                System.out.println(" Menu. \n 1. insertarEmpleado\n 2. consultarEmpleados\n 3. actualizarEmpleado\n 4. eliminarEmpleado");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("nombre");
                        String nombre = scanner.next();
                        System.out.println("cargo");
                        String cargo = scanner.next();
                        System.out.println("salario ");
                        Double salario = scanner.nextDouble();
                        insertarEmpleado(myConn, nombre, cargo, salario );
                        break;
                    case 2:
                        consultarEmpleados(myConn);
                        break;
                    case 3:
                        System.out.println("id");
                        int id = scanner.nextInt();
                        System.out.println("nombre");
                        String Newnombre = scanner.next();
                        System.out.println("cargo");
                        String Newcargo = scanner.next();
                        System.out.println("salario ");
                        Double Newsalario = scanner.nextDouble();
                        actualizarEmpleados(myConn, id, Newnombre, Newcargo, Newsalario );
                        break;
                    case 4:
                        System.out.println("id");
                        int idDrop = scanner.nextInt();
                        eliminarEmpleado(myConn, idDrop);
                        break;
                    default:
                        break;

                }
            }while (option != 5) ;


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo salio mal :(");
        }



    }

    // Método para insertar un empleado
    private static void insertarEmpleado(Connection conexion, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "INSERT INTO Empleados ( nombre, cargo, salario) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    // Método para consultar empleados
    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "select * from Empleados";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("id: %d, nombre: %s, cargo: %s, salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"));
            }
        }
    }

    // Método para actualizar un empleado
    private static void actualizarEmpleados(Connection conexion, int id, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "update Empleados set  nombre = ?, cargo = ?, salario = ? where id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    // Método para eliminar un empleado
    private static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "delete from Empleados where id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}


