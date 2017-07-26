package com.example.vn008xw.carbeat.ui.account;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.vn008xw.carbeat.data.repository.account.AccountRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class AccountViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
  @Mock
  AccountRepository accountRepository;
  private AccountViewModel viewModel;

  @Before
  public void setupAccountViewModelTest() {
    MockitoAnnotations.initMocks(this);
    viewModel = new AccountViewModel(accountRepository);
  }

  @Test
  public void createAccount_save() {
    final String name = "blah";
    final String last = "last";
    final String email = "jack@reacher.com";
    final String pin = "0000";
    viewModel.createAccount(name, last, email, pin);
    verify(accountRepository).insertAccount(name, last, email, pin);
  }

  @Test
  public void logout() {
    viewModel.logout();
    verify(accountRepository).logout();
  }
}
