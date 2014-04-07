package ghostdriver.tests;

import static org.testng.Assert.assertEquals;
import ghostdriver.pageobjects.HomePage;
import ghostdriver.pageobjects.MapaSitePage;

import org.testng.annotations.Test;

public class MapaSiteTest extends BaseTest
{
	@Test
	public void verificarNumeroCategorias()
	{
		HomePage home = new HomePage();
		MapaSitePage mapaSite = home.abrirMapaSite();
		
		assertEquals(23, mapaSite.getNumeroCategorias());
	}
	
	@Test
	public void verificarNumeroProdutos()
	{
		HomePage home = new HomePage();
		MapaSitePage mapaSite = home.abrirMapaSite();
		
		assertEquals(43, mapaSite.getNumeroProdutos());
	}
}
