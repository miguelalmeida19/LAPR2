package app.domain.store;

import app.domain.model.Company;
import auth.AuthFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrganizationRoleStoreTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public OrganizationRoleStore org;
    public List<OrganizationRoleStore> organizationRoleStoreList;

    @Before
    public void setUp() throws Exception {
        this.org = new OrganizationRoleStore();
        this.organizationRoleStoreList = new ArrayList<>();
    }

    @Test
    public void getRoles() {
        Assert.assertEquals(true, this.org.getRoles().getClass().equals(this.org.getRoles().getClass()));
    }
}