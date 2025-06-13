package com.interstellar.rahulpihujetpackdemo.rootGraph.graph.old


// 7. HOME GRAPH (Equivalent to home_graph.xml)
//fun NavGraphBuilder.homeGraph(
//    globalActions: GlobalNavigationActions,
//    authManager: AuthManager
//) {
//    navigation(
//        startDestination = HomeRoutes.Main.route, // Start destination is Main/Home
//        route = NavGraphs.HOME
//    ) {
//
//        // Main Home Fragment (with Bottom Navigation)
//        composable(
//            route = HomeRoutes.Main.route,
//            enterTransition = { NavigationAnimations.slideInRight },
//            exitTransition = { NavigationAnimations.slideOutLeft }
//        ) {
//            val localNavController = LocalNavController.current
//            MainHomeScreen(
//                localNavController = localNavController,
//                globalActions = globalActions,
//                authManager = authManager
//            )
//        }
//
//        // Products Fragment
//        composable(
//            route = HomeRoutes.Products.route,
//            enterTransition = { NavigationAnimations.slideInRight },
//            exitTransition = { NavigationAnimations.slideOutLeft }
//        ) {
//            val localNavController = LocalNavController.current
//            ProductsScreen(
//                onProductClick = { productId ->
//                    localNavController.navigate(
//                        HomeRoutes.ProductDetail.createRoute(productId)
//                    )
//                },
//                onBackPressed = {
//                    localNavController.popBackStack()
//                }
//            )
//        }
//
//        // Product Detail Fragment
//        composable(
//            route = HomeRoutes.ProductDetail.route,
//            arguments = listOf(navArgument("productId") { type = NavType.StringType }),
//            enterTransition = { NavigationAnimations.slideInRight },
//            exitTransition = { NavigationAnimations.slideOutLeft }
//        ) { backStackEntry ->
//            val localNavController = LocalNavController.current
//            val productId = backStackEntry.arguments?.getString("productId") ?: ""
//
//
//            ProductDetailScreen(
//                productId = productId,
//                onAddToCart = { id, name, price ->
//                    // ✅ Navigate to CartDetail with product data
//                    localNavController.navigate(
//                        HomeRoutes.CartDetail.createRoute(id, name, price)
//                    )
//                },
//                onBackPressed = {
//                    localNavController.popBackStack()
//                }
//            )
//        }
//
//
//        // ✅ NEW: Cart Detail Fragment
//
//        composable(
//            route = HomeRoutes.CartDetail.route,
//            arguments = listOf(
//                navArgument("productId") { type = NavType.StringType },
//                navArgument("productName") { type = NavType.StringType },
//                navArgument("productPrice") { type = NavType.StringType }
//            ),
//            enterTransition = { NavigationAnimations.slideInRight },
//            exitTransition = { NavigationAnimations.slideOutLeft }
//        ){
//                backStackEntry ->
//            val localNavController = LocalNavController.current
//            val productId = backStackEntry.arguments?.getString("productId") ?: ""
//            val productName = backStackEntry.arguments?.getString("productName") ?: ""
//            val productPrice = backStackEntry.arguments?.getString("productPrice") ?: ""
//
//            CartDetailScreen(
//                productId = productId,
//                productName = productName,
//                productPrice = productPrice,
//                onGoHome = {
//                    // ✅ Clear all stack and go to Home root
//                    localNavController.navigate(HomeRoutes.Main.route) {
//                        popUpTo(HomeRoutes.Main.route) {
//                            inclusive = true
//                        }
//                        //popUpTo(0)
////                        popUpTo(NavGraphs.HOME) {  // Pop to the HOME graph root
////                            inclusive = false
////                        }
////                        launchSingleTop = true  // Prevent multiple instances
//                    }
//                },
//
//                onBackToProducts = {
//
//                    // ✅ Just go back to existing Products screen (preserves state)
//                    localNavController.popBackStack(
//                        route = HomeRoutes.Products.route,
//                        inclusive = false
//                    )
//                },
//
//                onShareProduct = { shareText ->
//                    // ✅ Handle sharing (you can implement this)
//                    println("Share: $shareText")
//                }
//            )
//        }
//    }
//}
