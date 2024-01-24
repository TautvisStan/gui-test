using System.Windows.Forms;

namespace UI
{
    partial class Analyzer
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.TooSmallControlCheck = new System.Windows.Forms.CheckBox();
            this.TooLargeControlCheck = new System.Windows.Forms.CheckBox();
            this.HiddenControlCheck = new System.Windows.Forms.CheckBox();
            this.InsufficientSpaceCheck = new System.Windows.Forms.CheckBox();
            this.InvisibleControlCheck = new System.Windows.Forms.CheckBox();
            this.NoMarginsControlCheck = new System.Windows.Forms.CheckBox();
            this.PoorChoiceOfColorsCheck = new System.Windows.Forms.CheckBox();
            this.LowContrastCheck = new System.Windows.Forms.CheckBox();
            this.EmptyViewCheck = new System.Windows.Forms.CheckBox();
            this.NonCenteredCheck = new System.Windows.Forms.CheckBox();
            this.UnalignedControlsCheck = new System.Windows.Forms.CheckBox();
            this.ClippedControlCheck = new System.Windows.Forms.CheckBox();
            this.ObscuredControlCheck = new System.Windows.Forms.CheckBox();
            this.WrongLanguageCheck = new System.Windows.Forms.CheckBox();
            this.ObscuredTextCheck = new System.Windows.Forms.CheckBox();
            this.GrammarCheck = new System.Windows.Forms.CheckBox();
            this.WrongEncodingCheck = new System.Windows.Forms.CheckBox();
            this.ClippedTextCheck = new System.Windows.Forms.CheckBox();
            this.label1 = new System.Windows.Forms.Label();
            this.UnlocalizedIconsCheck = new System.Windows.Forms.CheckBox();
            this.MissingTranslationCheck = new System.Windows.Forms.CheckBox();
            this.MixedLanguagesStateCheck = new System.Windows.Forms.CheckBox();
            this.MixedLanguagesAppCheck = new System.Windows.Forms.CheckBox();
            this.OffensiveMessagesCheck = new System.Windows.Forms.CheckBox();
            this.MissingTextCheck = new System.Windows.Forms.CheckBox();
            this.TooHardToUnderstandCheck = new System.Windows.Forms.CheckBox();
            this.UnreadableTextCheck = new System.Windows.Forms.CheckBox();
            this.SelectAPKfile = new System.Windows.Forms.Button();
            this.RunDroidBot = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.IsEmulator = new System.Windows.Forms.CheckBox();
            this.TimeoutCheckBox = new System.Windows.Forms.CheckBox();
            this.SecondsTextbox = new System.Windows.Forms.TextBox();
            this.secondsLabel = new System.Windows.Forms.Label();
            this.DPIBox = new System.Windows.Forms.TextBox();
            this.WidthBox = new System.Windows.Forms.TextBox();
            this.HeightBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(12, 427);
            this.button1.Margin = new System.Windows.Forms.Padding(2);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(141, 19);
            this.button1.TabIndex = 0;
            this.button1.Text = "Select Screenshot Folder";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(12, 452);
            this.button2.Margin = new System.Windows.Forms.Padding(2);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(141, 19);
            this.button2.TabIndex = 1;
            this.button2.Text = "Analyze Screenshots";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // TooSmallControlCheck
            // 
            this.TooSmallControlCheck.AutoSize = true;
            this.TooSmallControlCheck.Location = new System.Drawing.Point(19, 121);
            this.TooSmallControlCheck.Margin = new System.Windows.Forms.Padding(2);
            this.TooSmallControlCheck.Name = "TooSmallControlCheck";
            this.TooSmallControlCheck.Size = new System.Drawing.Size(109, 17);
            this.TooSmallControlCheck.TabIndex = 2;
            this.TooSmallControlCheck.Text = "Too Small Control";
            this.TooSmallControlCheck.UseVisualStyleBackColor = true;
            this.TooSmallControlCheck.CheckedChanged += new System.EventHandler(this.TooSmallControlCheck_CheckedChanged);
            // 
            // TooLargeControlCheck
            // 
            this.TooLargeControlCheck.AutoSize = true;
            this.TooLargeControlCheck.Location = new System.Drawing.Point(19, 142);
            this.TooLargeControlCheck.Margin = new System.Windows.Forms.Padding(2);
            this.TooLargeControlCheck.Name = "TooLargeControlCheck";
            this.TooLargeControlCheck.Size = new System.Drawing.Size(111, 17);
            this.TooLargeControlCheck.TabIndex = 3;
            this.TooLargeControlCheck.Text = "Too Large Control";
            this.TooLargeControlCheck.UseVisualStyleBackColor = true;
            this.TooLargeControlCheck.CheckedChanged += new System.EventHandler(this.TooLargeControlCheck_CheckedChanged);
            // 
            // HiddenControlCheck
            // 
            this.HiddenControlCheck.AutoSize = true;
            this.HiddenControlCheck.Location = new System.Drawing.Point(19, 163);
            this.HiddenControlCheck.Margin = new System.Windows.Forms.Padding(2);
            this.HiddenControlCheck.Name = "HiddenControlCheck";
            this.HiddenControlCheck.Size = new System.Drawing.Size(96, 17);
            this.HiddenControlCheck.TabIndex = 4;
            this.HiddenControlCheck.Text = "Hidden Control";
            this.HiddenControlCheck.UseVisualStyleBackColor = true;
            this.HiddenControlCheck.CheckedChanged += new System.EventHandler(this.HiddenControlCheck_CheckedChanged);
            // 
            // InsufficientSpaceCheck
            // 
            this.InsufficientSpaceCheck.AutoSize = true;
            this.InsufficientSpaceCheck.Location = new System.Drawing.Point(19, 184);
            this.InsufficientSpaceCheck.Margin = new System.Windows.Forms.Padding(2);
            this.InsufficientSpaceCheck.Name = "InsufficientSpaceCheck";
            this.InsufficientSpaceCheck.Size = new System.Drawing.Size(111, 17);
            this.InsufficientSpaceCheck.TabIndex = 5;
            this.InsufficientSpaceCheck.Text = "Insufficient Space";
            this.InsufficientSpaceCheck.UseVisualStyleBackColor = true;
            this.InsufficientSpaceCheck.CheckedChanged += new System.EventHandler(this.InsufficientSpaceCheck_CheckedChanged);
            // 
            // InvisibleControlCheck
            // 
            this.InvisibleControlCheck.AutoSize = true;
            this.InvisibleControlCheck.Location = new System.Drawing.Point(19, 204);
            this.InvisibleControlCheck.Margin = new System.Windows.Forms.Padding(2);
            this.InvisibleControlCheck.Name = "InvisibleControlCheck";
            this.InvisibleControlCheck.Size = new System.Drawing.Size(100, 17);
            this.InvisibleControlCheck.TabIndex = 6;
            this.InvisibleControlCheck.Text = "Invisible Control";
            this.InvisibleControlCheck.UseVisualStyleBackColor = true;
            this.InvisibleControlCheck.CheckedChanged += new System.EventHandler(this.InvisibleControlCheck_CheckedChanged);
            // 
            // NoMarginsControlCheck
            // 
            this.NoMarginsControlCheck.AutoSize = true;
            this.NoMarginsControlCheck.Location = new System.Drawing.Point(19, 227);
            this.NoMarginsControlCheck.Margin = new System.Windows.Forms.Padding(2);
            this.NoMarginsControlCheck.Name = "NoMarginsControlCheck";
            this.NoMarginsControlCheck.Size = new System.Drawing.Size(116, 17);
            this.NoMarginsControlCheck.TabIndex = 7;
            this.NoMarginsControlCheck.Text = "No Margins Control";
            this.NoMarginsControlCheck.UseVisualStyleBackColor = true;
            this.NoMarginsControlCheck.CheckedChanged += new System.EventHandler(this.NoMarginsControlCheck_CheckedChanged);
            // 
            // PoorChoiceOfColorsCheck
            // 
            this.PoorChoiceOfColorsCheck.AutoSize = true;
            this.PoorChoiceOfColorsCheck.Location = new System.Drawing.Point(19, 247);
            this.PoorChoiceOfColorsCheck.Margin = new System.Windows.Forms.Padding(2);
            this.PoorChoiceOfColorsCheck.Name = "PoorChoiceOfColorsCheck";
            this.PoorChoiceOfColorsCheck.Size = new System.Drawing.Size(130, 17);
            this.PoorChoiceOfColorsCheck.TabIndex = 8;
            this.PoorChoiceOfColorsCheck.Text = "Poor Choice Of Colors";
            this.PoorChoiceOfColorsCheck.UseVisualStyleBackColor = true;
            this.PoorChoiceOfColorsCheck.CheckedChanged += new System.EventHandler(this.PoorChoiceOfColorsCheck_CheckedChanged);
            // 
            // LowContrastCheck
            // 
            this.LowContrastCheck.AutoSize = true;
            this.LowContrastCheck.Location = new System.Drawing.Point(19, 268);
            this.LowContrastCheck.Margin = new System.Windows.Forms.Padding(2);
            this.LowContrastCheck.Name = "LowContrastCheck";
            this.LowContrastCheck.Size = new System.Drawing.Size(88, 17);
            this.LowContrastCheck.TabIndex = 9;
            this.LowContrastCheck.Text = "Low Contrast";
            this.LowContrastCheck.UseVisualStyleBackColor = true;
            this.LowContrastCheck.CheckedChanged += new System.EventHandler(this.LowContrastCheck_CheckedChanged);
            // 
            // EmptyViewCheck
            // 
            this.EmptyViewCheck.AutoSize = true;
            this.EmptyViewCheck.Location = new System.Drawing.Point(19, 289);
            this.EmptyViewCheck.Margin = new System.Windows.Forms.Padding(2);
            this.EmptyViewCheck.Name = "EmptyViewCheck";
            this.EmptyViewCheck.Size = new System.Drawing.Size(81, 17);
            this.EmptyViewCheck.TabIndex = 10;
            this.EmptyViewCheck.Text = "Empty View";
            this.EmptyViewCheck.UseVisualStyleBackColor = true;
            this.EmptyViewCheck.CheckedChanged += new System.EventHandler(this.EmptyViewCheck_CheckedChanged);
            // 
            // NonCenteredCheck
            // 
            this.NonCenteredCheck.AutoSize = true;
            this.NonCenteredCheck.Location = new System.Drawing.Point(19, 310);
            this.NonCenteredCheck.Margin = new System.Windows.Forms.Padding(2);
            this.NonCenteredCheck.Name = "NonCenteredCheck";
            this.NonCenteredCheck.Size = new System.Drawing.Size(92, 17);
            this.NonCenteredCheck.TabIndex = 11;
            this.NonCenteredCheck.Text = "Non Centered";
            this.NonCenteredCheck.UseVisualStyleBackColor = true;
            this.NonCenteredCheck.CheckedChanged += new System.EventHandler(this.NonCenteredCheck_CheckedChanged);
            // 
            // UnalignedControlsCheck
            // 
            this.UnalignedControlsCheck.AutoSize = true;
            this.UnalignedControlsCheck.Location = new System.Drawing.Point(146, 121);
            this.UnalignedControlsCheck.Margin = new System.Windows.Forms.Padding(2);
            this.UnalignedControlsCheck.Name = "UnalignedControlsCheck";
            this.UnalignedControlsCheck.Size = new System.Drawing.Size(115, 17);
            this.UnalignedControlsCheck.TabIndex = 12;
            this.UnalignedControlsCheck.Text = "Unaligned Controls";
            this.UnalignedControlsCheck.UseVisualStyleBackColor = true;
            this.UnalignedControlsCheck.CheckedChanged += new System.EventHandler(this.UnalignedControlsCheck_CheckedChanged);
            // 
            // ClippedControlCheck
            // 
            this.ClippedControlCheck.AutoSize = true;
            this.ClippedControlCheck.Location = new System.Drawing.Point(146, 142);
            this.ClippedControlCheck.Margin = new System.Windows.Forms.Padding(2);
            this.ClippedControlCheck.Name = "ClippedControlCheck";
            this.ClippedControlCheck.Size = new System.Drawing.Size(97, 17);
            this.ClippedControlCheck.TabIndex = 13;
            this.ClippedControlCheck.Text = "Clipped Control";
            this.ClippedControlCheck.UseVisualStyleBackColor = true;
            this.ClippedControlCheck.CheckedChanged += new System.EventHandler(this.ClippedControlCheck_CheckedChanged);
            // 
            // ObscuredControlCheck
            // 
            this.ObscuredControlCheck.AutoSize = true;
            this.ObscuredControlCheck.Location = new System.Drawing.Point(146, 163);
            this.ObscuredControlCheck.Margin = new System.Windows.Forms.Padding(2);
            this.ObscuredControlCheck.Name = "ObscuredControlCheck";
            this.ObscuredControlCheck.Size = new System.Drawing.Size(108, 17);
            this.ObscuredControlCheck.TabIndex = 14;
            this.ObscuredControlCheck.Text = "Obscured Control";
            this.ObscuredControlCheck.UseVisualStyleBackColor = true;
            this.ObscuredControlCheck.CheckedChanged += new System.EventHandler(this.ObscuredControlCheck_CheckedChanged);
            // 
            // WrongLanguageCheck
            // 
            this.WrongLanguageCheck.AutoSize = true;
            this.WrongLanguageCheck.Location = new System.Drawing.Point(146, 184);
            this.WrongLanguageCheck.Margin = new System.Windows.Forms.Padding(2);
            this.WrongLanguageCheck.Name = "WrongLanguageCheck";
            this.WrongLanguageCheck.Size = new System.Drawing.Size(109, 17);
            this.WrongLanguageCheck.TabIndex = 15;
            this.WrongLanguageCheck.Text = "Wrong Language";
            this.WrongLanguageCheck.UseVisualStyleBackColor = true;
            this.WrongLanguageCheck.CheckedChanged += new System.EventHandler(this.WrongLanguageCheck_CheckedChanged);
            // 
            // ObscuredTextCheck
            // 
            this.ObscuredTextCheck.AutoSize = true;
            this.ObscuredTextCheck.Location = new System.Drawing.Point(146, 205);
            this.ObscuredTextCheck.Margin = new System.Windows.Forms.Padding(2);
            this.ObscuredTextCheck.Name = "ObscuredTextCheck";
            this.ObscuredTextCheck.Size = new System.Drawing.Size(96, 17);
            this.ObscuredTextCheck.TabIndex = 16;
            this.ObscuredTextCheck.Text = "Obscured Text";
            this.ObscuredTextCheck.UseVisualStyleBackColor = true;
            this.ObscuredTextCheck.CheckedChanged += new System.EventHandler(this.ObscuredTextCheck_CheckedChanged);
            // 
            // GrammarCheck
            // 
            this.GrammarCheck.AutoSize = true;
            this.GrammarCheck.Location = new System.Drawing.Point(146, 225);
            this.GrammarCheck.Margin = new System.Windows.Forms.Padding(2);
            this.GrammarCheck.Name = "GrammarCheck";
            this.GrammarCheck.Size = new System.Drawing.Size(102, 17);
            this.GrammarCheck.TabIndex = 17;
            this.GrammarCheck.Text = "Grammar Check";
            this.GrammarCheck.UseVisualStyleBackColor = true;
            this.GrammarCheck.CheckedChanged += new System.EventHandler(this.GrammarCheck_CheckedChanged);
            // 
            // WrongEncodingCheck
            // 
            this.WrongEncodingCheck.AutoSize = true;
            this.WrongEncodingCheck.Location = new System.Drawing.Point(146, 247);
            this.WrongEncodingCheck.Margin = new System.Windows.Forms.Padding(2);
            this.WrongEncodingCheck.Name = "WrongEncodingCheck";
            this.WrongEncodingCheck.Size = new System.Drawing.Size(106, 17);
            this.WrongEncodingCheck.TabIndex = 18;
            this.WrongEncodingCheck.Text = "Wrong Encoding";
            this.WrongEncodingCheck.UseVisualStyleBackColor = true;
            this.WrongEncodingCheck.CheckedChanged += new System.EventHandler(this.WrongEncodingCheck_CheckedChanged);
            // 
            // ClippedTextCheck
            // 
            this.ClippedTextCheck.AutoSize = true;
            this.ClippedTextCheck.Location = new System.Drawing.Point(146, 268);
            this.ClippedTextCheck.Margin = new System.Windows.Forms.Padding(2);
            this.ClippedTextCheck.Name = "ClippedTextCheck";
            this.ClippedTextCheck.Size = new System.Drawing.Size(85, 17);
            this.ClippedTextCheck.TabIndex = 19;
            this.ClippedTextCheck.Text = "Clipped Text";
            this.ClippedTextCheck.UseVisualStyleBackColor = true;
            this.ClippedTextCheck.CheckedChanged += new System.EventHandler(this.ClippedTextCheck_CheckedChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(177, 430);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(133, 13);
            this.label1.TabIndex = 20;
            this.label1.Text = "Current Screenshot Folder:";
            // 
            // UnlocalizedIconsCheck
            // 
            this.UnlocalizedIconsCheck.AutoSize = true;
            this.UnlocalizedIconsCheck.Location = new System.Drawing.Point(146, 289);
            this.UnlocalizedIconsCheck.Margin = new System.Windows.Forms.Padding(2);
            this.UnlocalizedIconsCheck.Name = "UnlocalizedIconsCheck";
            this.UnlocalizedIconsCheck.Size = new System.Drawing.Size(110, 17);
            this.UnlocalizedIconsCheck.TabIndex = 21;
            this.UnlocalizedIconsCheck.Text = "Unlocalized Icons";
            this.UnlocalizedIconsCheck.UseVisualStyleBackColor = true;
            this.UnlocalizedIconsCheck.CheckedChanged += new System.EventHandler(this.UnlocalizedIconsCheck_CheckedChanged);
            // 
            // MissingTranslationCheck
            // 
            this.MissingTranslationCheck.AutoSize = true;
            this.MissingTranslationCheck.Location = new System.Drawing.Point(146, 310);
            this.MissingTranslationCheck.Margin = new System.Windows.Forms.Padding(2);
            this.MissingTranslationCheck.Name = "MissingTranslationCheck";
            this.MissingTranslationCheck.Size = new System.Drawing.Size(116, 17);
            this.MissingTranslationCheck.TabIndex = 22;
            this.MissingTranslationCheck.Text = "Missing Translation";
            this.MissingTranslationCheck.UseVisualStyleBackColor = true;
            this.MissingTranslationCheck.CheckedChanged += new System.EventHandler(this.MissingTranslationCheck_CheckedChanged);
            // 
            // MixedLanguagesStateCheck
            // 
            this.MixedLanguagesStateCheck.AutoSize = true;
            this.MixedLanguagesStateCheck.Location = new System.Drawing.Point(146, 330);
            this.MixedLanguagesStateCheck.Margin = new System.Windows.Forms.Padding(2);
            this.MixedLanguagesStateCheck.Name = "MixedLanguagesStateCheck";
            this.MixedLanguagesStateCheck.Size = new System.Drawing.Size(138, 17);
            this.MixedLanguagesStateCheck.TabIndex = 23;
            this.MixedLanguagesStateCheck.Text = "Mixed Languages State";
            this.MixedLanguagesStateCheck.UseVisualStyleBackColor = true;
            this.MixedLanguagesStateCheck.CheckedChanged += new System.EventHandler(this.MixedLanguagesStateCheck_CheckedChanged);
            // 
            // MixedLanguagesAppCheck
            // 
            this.MixedLanguagesAppCheck.AutoSize = true;
            this.MixedLanguagesAppCheck.Location = new System.Drawing.Point(146, 351);
            this.MixedLanguagesAppCheck.Margin = new System.Windows.Forms.Padding(2);
            this.MixedLanguagesAppCheck.Name = "MixedLanguagesAppCheck";
            this.MixedLanguagesAppCheck.Size = new System.Drawing.Size(132, 17);
            this.MixedLanguagesAppCheck.TabIndex = 24;
            this.MixedLanguagesAppCheck.Text = "Mixed Languages App";
            this.MixedLanguagesAppCheck.UseVisualStyleBackColor = true;
            this.MixedLanguagesAppCheck.CheckedChanged += new System.EventHandler(this.MixedLanguagesAppCheck_CheckedChanged);
            // 
            // OffensiveMessagesCheck
            // 
            this.OffensiveMessagesCheck.AutoSize = true;
            this.OffensiveMessagesCheck.Location = new System.Drawing.Point(19, 372);
            this.OffensiveMessagesCheck.Margin = new System.Windows.Forms.Padding(2);
            this.OffensiveMessagesCheck.Name = "OffensiveMessagesCheck";
            this.OffensiveMessagesCheck.Size = new System.Drawing.Size(122, 17);
            this.OffensiveMessagesCheck.TabIndex = 25;
            this.OffensiveMessagesCheck.Text = "Offensive Messages";
            this.OffensiveMessagesCheck.UseVisualStyleBackColor = true;
            this.OffensiveMessagesCheck.CheckedChanged += new System.EventHandler(this.OffensiveMessagesCheck_CheckedChanged);
            // 
            // MissingTextCheck
            // 
            this.MissingTextCheck.AutoSize = true;
            this.MissingTextCheck.Location = new System.Drawing.Point(19, 330);
            this.MissingTextCheck.Margin = new System.Windows.Forms.Padding(2);
            this.MissingTextCheck.Name = "MissingTextCheck";
            this.MissingTextCheck.Size = new System.Drawing.Size(85, 17);
            this.MissingTextCheck.TabIndex = 26;
            this.MissingTextCheck.Text = "Missing Text";
            this.MissingTextCheck.UseVisualStyleBackColor = true;
            this.MissingTextCheck.CheckedChanged += new System.EventHandler(this.MissingTextCheck_CheckedChanged);
            // 
            // TooHardToUnderstandCheck
            // 
            this.TooHardToUnderstandCheck.AutoSize = true;
            this.TooHardToUnderstandCheck.Location = new System.Drawing.Point(146, 372);
            this.TooHardToUnderstandCheck.Margin = new System.Windows.Forms.Padding(2);
            this.TooHardToUnderstandCheck.Name = "TooHardToUnderstandCheck";
            this.TooHardToUnderstandCheck.Size = new System.Drawing.Size(145, 17);
            this.TooHardToUnderstandCheck.TabIndex = 27;
            this.TooHardToUnderstandCheck.Text = "Too Hard To Understand";
            this.TooHardToUnderstandCheck.UseVisualStyleBackColor = true;
            this.TooHardToUnderstandCheck.CheckedChanged += new System.EventHandler(this.TooHardToUnderstandCheck_CheckedChanged);
            // 
            // UnreadableTextCheck
            // 
            this.UnreadableTextCheck.AutoSize = true;
            this.UnreadableTextCheck.Location = new System.Drawing.Point(19, 351);
            this.UnreadableTextCheck.Margin = new System.Windows.Forms.Padding(2);
            this.UnreadableTextCheck.Name = "UnreadableTextCheck";
            this.UnreadableTextCheck.Size = new System.Drawing.Size(105, 17);
            this.UnreadableTextCheck.TabIndex = 28;
            this.UnreadableTextCheck.Text = "Unreadable Text";
            this.UnreadableTextCheck.UseVisualStyleBackColor = true;
            this.UnreadableTextCheck.CheckedChanged += new System.EventHandler(this.UnreadableTextCheck_CheckedChanged);
            // 
            // SelectAPKfile
            // 
            this.SelectAPKfile.Location = new System.Drawing.Point(9, 20);
            this.SelectAPKfile.Margin = new System.Windows.Forms.Padding(2);
            this.SelectAPKfile.Name = "SelectAPKfile";
            this.SelectAPKfile.Size = new System.Drawing.Size(141, 19);
            this.SelectAPKfile.TabIndex = 29;
            this.SelectAPKfile.Text = "Select APK file";
            this.SelectAPKfile.UseVisualStyleBackColor = true;
            this.SelectAPKfile.Click += new System.EventHandler(this.SelectAPKfile_Click);
            // 
            // RunDroidBot
            // 
            this.RunDroidBot.Location = new System.Drawing.Point(9, 43);
            this.RunDroidBot.Margin = new System.Windows.Forms.Padding(2);
            this.RunDroidBot.Name = "RunDroidBot";
            this.RunDroidBot.Size = new System.Drawing.Size(141, 19);
            this.RunDroidBot.TabIndex = 30;
            this.RunDroidBot.Text = "Run DroidBot";
            this.RunDroidBot.UseVisualStyleBackColor = true;
            this.RunDroidBot.Click += new System.EventHandler(this.RunDroidBot_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(177, 23);
            this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(88, 13);
            this.label2.TabIndex = 31;
            this.label2.Text = "No APK selected";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(94, 94);
            this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(95, 13);
            this.label3.TabIndex = 32;
            this.label3.Text = "Defects to analyze";
            // 
            // IsEmulator
            // 
            this.IsEmulator.AutoSize = true;
            this.IsEmulator.Location = new System.Drawing.Point(14, 66);
            this.IsEmulator.Margin = new System.Windows.Forms.Padding(2);
            this.IsEmulator.Name = "IsEmulator";
            this.IsEmulator.Size = new System.Drawing.Size(130, 17);
            this.IsEmulator.TabIndex = 33;
            this.IsEmulator.Text = "Running on emulator?";
            this.IsEmulator.UseVisualStyleBackColor = true;
            this.IsEmulator.CheckedChanged += new System.EventHandler(this.IsEmulator_CheckedChanged);
            // 
            // TimeoutCheckBox
            // 
            this.TimeoutCheckBox.AutoSize = true;
            this.TimeoutCheckBox.Location = new System.Drawing.Point(163, 46);
            this.TimeoutCheckBox.Margin = new System.Windows.Forms.Padding(2);
            this.TimeoutCheckBox.Name = "TimeoutCheckBox";
            this.TimeoutCheckBox.Size = new System.Drawing.Size(91, 17);
            this.TimeoutCheckBox.TabIndex = 34;
            this.TimeoutCheckBox.Text = "Timeout after ";
            this.TimeoutCheckBox.UseVisualStyleBackColor = true;
            this.TimeoutCheckBox.CheckedChanged += new System.EventHandler(this.TimeoutCheckBox_CheckedChanged);
            // 
            // SecondsTextbox
            // 
            this.SecondsTextbox.Location = new System.Drawing.Point(248, 43);
            this.SecondsTextbox.Margin = new System.Windows.Forms.Padding(2);
            this.SecondsTextbox.Name = "SecondsTextbox";
            this.SecondsTextbox.Size = new System.Drawing.Size(42, 20);
            this.SecondsTextbox.TabIndex = 35;
            this.SecondsTextbox.TextChanged += new System.EventHandler(this.SecondsTextbox_TextChanged);
            this.SecondsTextbox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.SecondsTextbox_KeyPress);
            // 
            // secondsLabel
            // 
            this.secondsLabel.AutoSize = true;
            this.secondsLabel.Location = new System.Drawing.Point(293, 46);
            this.secondsLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.secondsLabel.Name = "secondsLabel";
            this.secondsLabel.Size = new System.Drawing.Size(50, 13);
            this.secondsLabel.TabIndex = 36;
            this.secondsLabel.Text = "seconds.";
            // 
            // DPIBox
            // 
            this.DPIBox.Location = new System.Drawing.Point(179, 63);
            this.DPIBox.Margin = new System.Windows.Forms.Padding(2);
            this.DPIBox.Name = "DPIBox";
            this.DPIBox.Size = new System.Drawing.Size(30, 20);
            this.DPIBox.TabIndex = 37;
            this.DPIBox.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // WidthBox
            // 
            this.WidthBox.Location = new System.Drawing.Point(253, 65);
            this.WidthBox.Margin = new System.Windows.Forms.Padding(2);
            this.WidthBox.Name = "WidthBox";
            this.WidthBox.Size = new System.Drawing.Size(36, 20);
            this.WidthBox.TabIndex = 38;
            // 
            // HeightBox
            // 
            this.HeightBox.Location = new System.Drawing.Point(334, 64);
            this.HeightBox.Margin = new System.Windows.Forms.Padding(2);
            this.HeightBox.Name = "HeightBox";
            this.HeightBox.Size = new System.Drawing.Size(32, 20);
            this.HeightBox.TabIndex = 39;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(147, 67);
            this.label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(28, 13);
            this.label4.TabIndex = 40;
            this.label4.Text = "DPI:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(212, 68);
            this.label5.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(38, 13);
            this.label5.TabIndex = 41;
            this.label5.Text = "Width:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(289, 68);
            this.label6.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(41, 13);
            this.label6.TabIndex = 42;
            this.label6.Text = "Height:";
            // 
            // Analyzer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(587, 527);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.HeightBox);
            this.Controls.Add(this.WidthBox);
            this.Controls.Add(this.DPIBox);
            this.Controls.Add(this.secondsLabel);
            this.Controls.Add(this.SecondsTextbox);
            this.Controls.Add(this.TimeoutCheckBox);
            this.Controls.Add(this.IsEmulator);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.RunDroidBot);
            this.Controls.Add(this.SelectAPKfile);
            this.Controls.Add(this.UnreadableTextCheck);
            this.Controls.Add(this.TooHardToUnderstandCheck);
            this.Controls.Add(this.MissingTextCheck);
            this.Controls.Add(this.OffensiveMessagesCheck);
            this.Controls.Add(this.MixedLanguagesAppCheck);
            this.Controls.Add(this.MixedLanguagesStateCheck);
            this.Controls.Add(this.MissingTranslationCheck);
            this.Controls.Add(this.UnlocalizedIconsCheck);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.ClippedTextCheck);
            this.Controls.Add(this.WrongEncodingCheck);
            this.Controls.Add(this.GrammarCheck);
            this.Controls.Add(this.ObscuredTextCheck);
            this.Controls.Add(this.WrongLanguageCheck);
            this.Controls.Add(this.ObscuredControlCheck);
            this.Controls.Add(this.ClippedControlCheck);
            this.Controls.Add(this.UnalignedControlsCheck);
            this.Controls.Add(this.NonCenteredCheck);
            this.Controls.Add(this.EmptyViewCheck);
            this.Controls.Add(this.LowContrastCheck);
            this.Controls.Add(this.PoorChoiceOfColorsCheck);
            this.Controls.Add(this.NoMarginsControlCheck);
            this.Controls.Add(this.InvisibleControlCheck);
            this.Controls.Add(this.InsufficientSpaceCheck);
            this.Controls.Add(this.HiddenControlCheck);
            this.Controls.Add(this.TooLargeControlCheck);
            this.Controls.Add(this.TooSmallControlCheck);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.Name = "Analyzer";
            this.Text = "Analyzer";
            this.Load += new System.EventHandler(this.Analyzer_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.CheckBox TooSmallControlCheck;
        private System.Windows.Forms.CheckBox TooLargeControlCheck;
        private System.Windows.Forms.CheckBox HiddenControlCheck;
        private System.Windows.Forms.CheckBox InsufficientSpaceCheck;
        private System.Windows.Forms.CheckBox InvisibleControlCheck;
        private System.Windows.Forms.CheckBox NoMarginsControlCheck;
        private System.Windows.Forms.CheckBox PoorChoiceOfColorsCheck;
        private System.Windows.Forms.CheckBox LowContrastCheck;
        private System.Windows.Forms.CheckBox EmptyViewCheck;
        private System.Windows.Forms.CheckBox NonCenteredCheck;
        private System.Windows.Forms.CheckBox UnalignedControlsCheck;
        private System.Windows.Forms.CheckBox ClippedControlCheck;
        private System.Windows.Forms.CheckBox ObscuredControlCheck;
        private System.Windows.Forms.CheckBox WrongLanguageCheck;
        private System.Windows.Forms.CheckBox ObscuredTextCheck;
        private System.Windows.Forms.CheckBox GrammarCheck;
        private System.Windows.Forms.CheckBox WrongEncodingCheck;
        private System.Windows.Forms.CheckBox ClippedTextCheck;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.CheckBox UnlocalizedIconsCheck;
        private System.Windows.Forms.CheckBox MissingTranslationCheck;
        private System.Windows.Forms.CheckBox MixedLanguagesStateCheck;
        private System.Windows.Forms.CheckBox MixedLanguagesAppCheck;
        private System.Windows.Forms.CheckBox OffensiveMessagesCheck;
        private System.Windows.Forms.CheckBox MissingTextCheck;
        private System.Windows.Forms.CheckBox TooHardToUnderstandCheck;
        private System.Windows.Forms.CheckBox UnreadableTextCheck;
        private System.Windows.Forms.Button SelectAPKfile;
        private System.Windows.Forms.Button RunDroidBot;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.CheckBox IsEmulator;
        private System.Windows.Forms.CheckBox TimeoutCheckBox;
        private System.Windows.Forms.TextBox SecondsTextbox;
        private System.Windows.Forms.Label secondsLabel;
        private TextBox DPIBox;
        private TextBox WidthBox;
        private TextBox HeightBox;
        private Label label4;
        private Label label5;
        private Label label6;
    }
}

