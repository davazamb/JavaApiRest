package JavaApiRest.DzJavaProducts.repository;

import JavaApiRest.DzJavaProducts.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para encontrar usuarios por nombre
    List<Usuario> findByNombre(String nombre);

    // Método para encontrar un usuario por correo electrónico
    Optional<Usuario> findByEmail(String email);

    // Método para encontrar usuarios cuyo nombre contiene una cadena específica (búsqueda parcial)
    List<Usuario> findByNombreContaining(String partialName);
}
