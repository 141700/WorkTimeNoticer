package ru.massandrashop.worktimenoticer.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.databinding.FragmentFirstBinding;
import org.jetbrains.annotations.NotNull;
import androidx.navigation.fragment.NavHostFragment;
import ru.massandrashop.worktimenoticer.service.notification.NotificationProcessService;
import ru.massandrashop.worktimenoticer.service.ui.IdTypeRadioGroupService;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    TextView showResultTextView;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View fragmentFirstLayout = binding.getRoot();

        IdTypeRadioGroupService.setButtonsInitialViewSettings(fragmentFirstLayout);
        IdTypeRadioGroupService.setOnCheckedChangeListener(fragmentFirstLayout);

        showResultTextView = fragmentFirstLayout.findViewById(R.id.textview_result);
        return fragmentFirstLayout;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonStart.setOnClickListener(view1 -> {
            Context context = view1.getContext();
            String result = NotificationProcessService.processNotification(context);
            showResultTextView.setText(result);
        });

        binding.buttonView.setOnClickListener(view12 -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}