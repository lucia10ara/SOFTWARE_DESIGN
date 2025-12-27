package ecoembes.dto;

public class CredentialsDTO {

	private String email;
    private String password;

    public CredentialsDTO() {}

    public CredentialsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
