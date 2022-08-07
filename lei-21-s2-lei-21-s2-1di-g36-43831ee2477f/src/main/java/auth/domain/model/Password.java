package auth.domain.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Password implements Serializable {

    private String password;

    public Password(String password)
    {
        if (!validate(password))
            throw new IllegalArgumentException("Invalid Email Address.");
        this.password = createHash(password);
    }

    public String getPassword() {
        return password;
    }

    private boolean validate(String password) {
        if (StringUtils.isBlank(password))
            return false;
        // Check for other invalid criteria here

        //
        return true;
    }

    private String createHash(String password)
    {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST,password.toCharArray());
    }

    public boolean checkPassword(String pwd)
    {
        if (StringUtils.isBlank(pwd))
            return false;
        BCrypt.Result result = BCrypt.verifyer().verify(pwd.toCharArray(),this.password.toCharArray());
        return result.verified;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 7 * hash + this.password.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        // Inspired in https://www.sitepoint.com/implement-javas-equals-method-correctly/

        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        // field comparison
        Password obj = (Password) o;
        return Objects.equals(this.password, obj.password);
    }
}
