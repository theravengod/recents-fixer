package kitty.cheshire.recents.fixer

import kitty.cheshire.recents.fixer.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { parameters ->
        MainViewModel()
    }
}