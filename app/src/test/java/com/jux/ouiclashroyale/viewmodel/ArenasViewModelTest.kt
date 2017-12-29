package com.jux.ouiclashroyale.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.view.View
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.repository.ArenasRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class ArenasViewModelTest {
    private inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock private val dataSource = mock(ArenasRepository::class.java)

    private lateinit var viewModel: ArenasViewModel

    @Before
    fun setup() {
        viewModel = ArenasViewModel()
        viewModel.setDataSource(dataSource)
    }

    @Test
    fun getArenasTest() {
        // GIVEN
        val loadingObserver = mock<Observer<Boolean>>()
        viewModel.loading.observeForever(loadingObserver)

        // WHEN
        viewModel.getArenas()

        // THEN
        verify(loadingObserver).onChanged(true)
        verify(dataSource).getArenas(viewModel)
    }

    @Test
    fun refreshArenasTest() {
        // GIVEN
        val loadingObserver = mock<Observer<Boolean>>()
        viewModel.loading.observeForever(loadingObserver)

        // WHEN
        viewModel.refreshArenas()

        // THEN
        verify(loadingObserver).onChanged(true)
        verify(dataSource).getArenas(viewModel)
    }

    @Test
    fun onArenasLoadedTest() {
        // GIVEN
        val arenas = arrayOf(
                Arena("id", "id_name", "name", 100),
                Arena("id2", "id_name_2", "name_2", 200)
        )

        val arenasObserver = mock<Observer<Array<Arena>>>()
        val loadingObserver = mock<Observer<Boolean>>()
        val listViewVisibilityObserver = mock<Observer<Int>>()
        val emptyViewVisibilityObserver = mock<Observer<Int>>()

        viewModel.getArenas()?.observeForever(arenasObserver)
        viewModel.loading.observeForever(loadingObserver)
        viewModel.listViewVisibility.observeForever(listViewVisibilityObserver)
        viewModel.emptyViewVisibility.observeForever(emptyViewVisibilityObserver)

        // WHEN
        viewModel.onArenasLoaded(arenas)

        // THEN
        verify(arenasObserver).onChanged(arenas)
        verify(loadingObserver).onChanged(false)
        verify(listViewVisibilityObserver).onChanged(View.VISIBLE)
        verify(emptyViewVisibilityObserver).onChanged(View.GONE)
    }

    @Test
    fun onArenasLoadedEmptyArrayTest() {
        // GIVEN
        val arenas = arrayOf<Arena>()

        val arenasObserver = mock<Observer<Array<Arena>>>()
        val loadingObserver = mock<Observer<Boolean>>()
        val listViewVisibilityObserver = mock<Observer<Int>>()
        val emptyViewVisibilityObserver = mock<Observer<Int>>()

        viewModel.getArenas()?.observeForever(arenasObserver)
        viewModel.loading.observeForever(loadingObserver)
        viewModel.listViewVisibility.observeForever(listViewVisibilityObserver)
        viewModel.emptyViewVisibility.observeForever(emptyViewVisibilityObserver)

        // WHEN
        viewModel.onArenasLoaded(arenas)

        // THEN
        verify(arenasObserver).onChanged(arenas)
        verify(loadingObserver).onChanged(false)
        verify(listViewVisibilityObserver).onChanged(View.GONE)
        verify(emptyViewVisibilityObserver).onChanged(View.VISIBLE)
    }

    @Test
    fun onErrorTest() {
        // GIVEN
        val loadingObserver = mock<Observer<Boolean>>()
        val listViewVisibilityObserver = mock<Observer<Int>>()
        val emptyViewVisibilityObserver = mock<Observer<Int>>()
        val errorObserver = mock<Observer<String>>()

        viewModel.loading.observeForever(loadingObserver)
        viewModel.listViewVisibility.observeForever(listViewVisibilityObserver)
        viewModel.emptyViewVisibility.observeForever(emptyViewVisibilityObserver)
        viewModel.error.observeForever(errorObserver)

        // WHEN
        viewModel.onError()

        // THEN
        verify(loadingObserver).onChanged(false)
        verify(listViewVisibilityObserver).onChanged(View.GONE)
        verify(emptyViewVisibilityObserver).onChanged(View.VISIBLE)
        verify(errorObserver).onChanged("Couldn't loadArenaImage arenas")
    }
}