using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection.Emit;
using System.Security.Policy;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace UI
{
    public partial class Analyzer : Form
    {
        private enum Rules
        {
            TooSmallControlCheck,
            TooLargeControlCheck,
            HiddenControlCheck,
            InsufficientSpaceCheck,
            InvisibleControlCheck,
            NoMarginsControlCheck,
            PoorChoiceOfColorsCheck,
            LowContrastCheck,
            EmptyViewCheck,
            NonCenteredCheck,

            UnalignedControlsCheck,
            ClippedControlCheck,
            ObscuredControlCheck,
            WrongLanguageCheck,
            ObscuredTextCheck,
            GrammarCheck,
            WrongEncodingCheck,
            ClippedTextCheck,
            UnlocalizedIconsCheck,
            MissingTranslationCheck,
            MixedLanguagesStateCheck,
            MixedLanguagesAppCheck,
            OffensiveMessagesCheck,
            UnreadableTextCheck,
            TooHardToUnderstandCheck,
            MissingTextCheck
        }
        private static List<string> SelectedRules = new List<string>();
        private static string ScreenshotDirectory = "";
        private static string APKFile = "";
        private System.Diagnostics.Process cmdProcess = null;
        private static bool Emulator = false;
        private static bool Timeout = false;
        private static int TimeoutSecs = 0;
        private void HandleCheckBox(CheckBox ruleCheckbox, Rules rule)
        {
            if (ruleCheckbox.Checked)
            {
                if (!SelectedRules.Contains(rule.ToString()))
                    SelectedRules.Add(rule.ToString());
            }
            else
            {
                if (SelectedRules.Contains(rule.ToString()))
                    SelectedRules.Remove(rule.ToString());
            }
        }
        public Analyzer()
        {
            InitializeComponent();
        }

        private void Analyzer_Load(object sender, EventArgs e)
        {
            HandleCheckBox(HiddenControlCheck, Rules.HiddenControlCheck);
        }

        private void UnalignedControlsCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(UnalignedControlsCheck, Rules.UnalignedControlsCheck);
        }

        private void ClippedControlCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(ClippedControlCheck, Rules.ClippedControlCheck);
        }

        private void ObscuredControlCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(ObscuredControlCheck, Rules.ObscuredControlCheck);
        }

        private void WrongLanguageCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(WrongLanguageCheck, Rules.WrongLanguageCheck);
        }

        private void ObscuredTextCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(ObscuredTextCheck, Rules.ObscuredTextCheck);
        }

        private void GrammarCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(GrammarCheck, Rules.GrammarCheck);
        }

        private void WrongEncodingCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(WrongEncodingCheck, Rules.WrongEncodingCheck);
        }

        private void TooSmallControlCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(TooSmallControlCheck, Rules.TooSmallControlCheck);
        }

        private void TooLargeControlCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(TooLargeControlCheck, Rules.TooLargeControlCheck);
        }

        private void HiddenControlCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(HiddenControlCheck, Rules.HiddenControlCheck);
        }

        private void InsufficientSpaceCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(InsufficientSpaceCheck, Rules.InsufficientSpaceCheck);
        }

        private void InvisibleControlCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(InvisibleControlCheck, Rules.InvisibleControlCheck);
        }

        private void NoMarginsControlCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(NoMarginsControlCheck, Rules.NoMarginsControlCheck);
        }

        private void PoorChoiceOfColorsCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(PoorChoiceOfColorsCheck, Rules.PoorChoiceOfColorsCheck);
        }

        private void LowContrastCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(LowContrastCheck, Rules.LowContrastCheck);
        }

        private void EmptyViewCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(EmptyViewCheck, Rules.EmptyViewCheck);
        }

        private void NonCenteredCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(NonCenteredCheck, Rules.NonCenteredCheck);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            using (var fbd = new FolderBrowserDialog())
            {
                DialogResult result = fbd.ShowDialog();

                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.SelectedPath))
                {
                    string[] files = Directory.GetFiles(fbd.SelectedPath);
                    ScreenshotDirectory = fbd.SelectedPath;
                    label1.Text = "Current Screenshot Folder: " + ScreenshotDirectory;
                }
                else
                {
                    ScreenshotDirectory = "";
                    label1.Text = "Current Screenshot Folder: " + ScreenshotDirectory;
                }
            }
        }

        private void TooHardToUnderstandCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(TooHardToUnderstandCheck, Rules.TooHardToUnderstandCheck);
        }

        private void ClippedTextCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(ClippedTextCheck, Rules.ClippedTextCheck);
        }

        private void UnlocalizedIconsCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(UnlocalizedIconsCheck, Rules.UnlocalizedIconsCheck);
        }

        private void MissingTranslationCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(MissingTranslationCheck, Rules.MissingTranslationCheck);
        }

        private void MixedLanguagesStateCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(MixedLanguagesStateCheck, Rules.MixedLanguagesStateCheck);
        }

        private void MixedLanguagesAppCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(MixedLanguagesAppCheck, Rules.MixedLanguagesAppCheck);
        }

        private void OffensiveMessagesCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(OffensiveMessagesCheck, Rules.OffensiveMessagesCheck);
        }

        private void UnreadableTextCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(UnreadableTextCheck, Rules.UnreadableTextCheck);
        }

        private void MissingTextCheck_CheckedChanged(object sender, EventArgs e)
        {
            HandleCheckBox(MissingTextCheck, Rules.MissingTextCheck);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            string applicationLocation = System.Reflection.Assembly.GetEntryAssembly().Location;
            string applicationDirectory = Path.GetDirectoryName(applicationLocation);

            if (ScreenshotDirectory == "")
            {
                MessageBox.Show("No screenshot directory selected.", "Error",
                     MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            if (SelectedRules.Count == 0)
            {
                MessageBox.Show("No rules selected.", "Error",
                        MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            if (cmdProcess != null)
            {
                if (!cmdProcess.HasExited)
                {
                    MessageBox.Show("Tool process is already running.", "Error",
                        MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
            }
            string strCmdText = "/C java -jar \"" + applicationDirectory + "\\DefectsAnalyzer.jar\" Analyze \"" + ScreenshotDirectory + "\"";
            foreach (string rule in SelectedRules)
            {
                strCmdText += " " + rule;
            }
  //          label1.Text = strCmdText;

            cmdProcess = System.Diagnostics.Process.Start("CMD.exe", strCmdText);
            //ExecuteCmd.ExecuteCommandAsync("java -jar C:\\Users\\AAA\\Downloads\\eclipse-java-2023-03-R-win32-x86_64\\Naujas aplankas\\test.jar Analyze C:\\gui\\_analyzer_\\apps\\ADSDroidFree_v0.0.2 TooSmallControlCheck EmptyViewCheck");
        }

        private void SelectAPKfile_Click(object sender, EventArgs e)
        {
            using (var fbd = new OpenFileDialog())
            {
                fbd.Filter = "APK Files |*.APK";
                DialogResult result = fbd.ShowDialog();

                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.FileName))
                {
                    APKFile = fbd.FileName;
                    label2.Text = "APK Selected: " + APKFile;
                }
                else
                {
                    APKFile = "";
                    label2.Text = "No APK Selected";
                }
            }
        }

        private void RunDroidBot_Click(object sender, EventArgs e)
        {
            string applicationLocation = System.Reflection.Assembly.GetEntryAssembly().Location;
            string applicationDirectory = Path.GetDirectoryName(applicationLocation);



            if (APKFile == "")
            {
                MessageBox.Show("No APK file selected.", "Error",
                     MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            if (cmdProcess != null)
            {
                if (!cmdProcess.HasExited)
                {
                    MessageBox.Show("Tool process is already running.", "Error",
                        MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
            }
            
            string strCmdText = "/C java -jar \"" + applicationDirectory + "\\DefectsAnalyzer.jar\" DroidBot \"" + APKFile + "\"";
            if (Emulator)
            {
                strCmdText += " -Emulator";
            }
            if (Timeout)
            {
                strCmdText += " -Timeout " + int.Parse(SecondsTextbox.Text);
            }
            label2.Text = strCmdText;

            cmdProcess = System.Diagnostics.Process.Start("CMD.exe", strCmdText);
        }

        private void IsEmulator_CheckedChanged(object sender, EventArgs e)
        {
            Emulator = IsEmulator.Checked;
        }

        private void SecondsTextbox_TextChanged(object sender, EventArgs e)
        {

        }

        private void TimeoutCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            Timeout = TimeoutCheckBox.Checked;
        }

        private void SecondsTextbox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsControl(e.KeyChar) && !char.IsDigit(e.KeyChar) && (e.KeyChar != '.'))
            {
                e.Handled = true;
            }
        }
    }
}
