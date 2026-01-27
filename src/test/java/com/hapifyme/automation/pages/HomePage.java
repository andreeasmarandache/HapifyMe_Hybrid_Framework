package com.hapifyme.automation.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private final SelenideElement userProfile = $(".user_details");
    private final SelenideElement postInput = $("textarea[name='post_text']");

    public void assertUserIsLoggedIn() {
        userProfile.shouldBe(Condition.visible);
    }

    public void createPost(String text) {
        postInput.setValue(text);
        $("input[name='submit']").click();
    }
}

