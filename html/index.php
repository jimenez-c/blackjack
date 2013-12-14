<?php
$page = (isset($_GET["p"])) ? $_GET["p"] : "home";

switch($page) {
	case "home" :
		$content = file_get_contents("ui/pages/home.html");
		$title = "Accueil";
	break;

	case "games" :
		$content = file_get_contents("ui/pages/games.html");
		$title = "Liste des parties";
	break;

	case "rules" :
		$content = file_get_contents("ui/pages/rules.html");
		$title = "Règles";
	break;

	case "login" :
		$content = file_get_contents("ui/pages/login.html");
		$title = "Connexion";
	break;

	case "registration" :
		$content = file_get_contents("ui/pages/registration.html");
		$title = "Inscription";
	break;

	case "game" :
		$content = file_get_contents("ui/pages/game.html");
		$title = "Jouer";
	break;

	case "contact" :
		$content = file_get_contents("ui/pages/contact.html");
		$title = "Contacter les développeurs";
	break;

	default:
		$content = $title = "Page non trouvée.";
}
$title .= ":: Black Jack";
$css = $page == "game" ? "game" : "site";
$layout = $page == "game" ? "game" : "site";
include("ui/pages/".$layout.".layout.html");

function active($page, $item) {
	if($item == $page) return "active";
}
?>