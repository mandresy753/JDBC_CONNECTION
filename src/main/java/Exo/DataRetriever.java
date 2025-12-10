package Exo;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "select * from product_category";

        try (Connection conn = new DBConnection().getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;

        String sql = """
        SELECT p.id, p.name, p.price, p.creation_datetime,
               pc.id AS category_id, pc.name AS category_name
        FROM product p
        LEFT JOIN product_category pc ON pc.product_id = p.id
        ORDER BY p.id
        LIMIT ? OFFSET ?
    """;

        try (Connection conn = new DBConnection().getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Category category = null;
                if (rs.getInt("category_id") != 0) {  // s'il y a une catégorie
                    category = new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name")
                    );
                }

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) {
        List<Product> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.name, p.creation_datetime, " +
                        "c.id AS category_id, c.name AS category_name " +
                        "FROM Product p " +
                        "LEFT JOIN Product_category c ON c.product_id = p.id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.isBlank()) {
            sql.append(" AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isBlank()) {
            sql.append(" AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        sql.append(" ORDER BY p.id ASC");

        try (Connection conn = new DBConnection().getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Category category = null;
                int categoryId = rs.getInt("category_id");
                if (!rs.wasNull()) {
                    category = new Category(
                            categoryId,
                            rs.getString("category_name")
                    );
                }

                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}
