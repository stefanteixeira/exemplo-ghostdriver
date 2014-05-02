package ghostdriver.tests;

import static org.testng.Assert.assertTrue;
import ghostdriver.pageobjects.CarrinhoComprasPage;
import ghostdriver.pageobjects.HomePage;

import org.testng.annotations.Test;

public class CarrinhoComprasTest extends BaseTest
{
	private static HomePage home;
	private static CarrinhoComprasPage carrinho;

	@Test
	public void adicionarLivroCarrinho()
	{
		home = new HomePage();
		carrinho = home.adicionarAoCarrinho("Fortaleza Digital");
		
		assertTrue(carrinho.isMsgSucessoPresente());
	}
}
